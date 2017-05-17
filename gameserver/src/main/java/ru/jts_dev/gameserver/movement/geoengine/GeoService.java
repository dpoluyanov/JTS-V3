/*
 * Copyright (c) 2015, 2016, 2017 JTS-Team authors and/or its affiliates. All rights reserved.
 *
 * This file is part of JTS-V3 Project.
 *
 * JTS-V3 Project is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTS-V3 Project is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTS-V3 Project.  If not, see <http://www.gnu.org/licenses/>.
 */

package ru.jts_dev.gameserver.movement.geoengine;

import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.movement.geoengine.block.ComplexBlock;
import ru.jts_dev.gameserver.movement.geoengine.block.FlatBlock;
import ru.jts_dev.gameserver.movement.geoengine.block.MultilevelBlock;
import ru.jts_dev.gameserver.movement.geoengine.model.Block;
import ru.jts_dev.gameserver.movement.geoengine.model.Cell;
import ru.jts_dev.gameserver.movement.geoengine.model.Region;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author Java-man
 * @author Pointer*Rage
 * @since 18.12.2015
 */
@Component
public class GeoService {
    private Region[][] regions = new Region[32][32];

    public void loadGeodata() {
        Path geodataDir = Paths.get("./data/geodata");
        try (Stream<Path> pathStream = Files.walk(geodataDir)) {
            pathStream.forEach(path -> {
                String name = path.getFileName().toString();
                int x = Integer.parseInt(name.substring(0, 2));
                int y = Integer.parseInt(name.substring(3, 5));

                try (FileChannel fileChannel = FileChannel.open(path)) {
                    MappedByteBuffer buffer = fileChannel.map(MapMode.READ_ONLY, 0, fileChannel.size());
                    buffer.order(ByteOrder.LITTLE_ENDIAN);
                    loadRegion(x, y, buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadRegion(int x, int y, ByteBuffer byteBuffer) {
        Region region = new Region();
        int index = 0;

        byteBuffer.position(18);
        while (byteBuffer.hasRemaining()) {
            short type = byteBuffer.getShort();

            if (type == 0x0000) {
                // 1x short, flat block
                short maxHeight = (short) (byteBuffer.getShort() & 0x0fff0);
                short minHeight = (short) (byteBuffer.getShort() & 0x0fff0);

                Block block = new FlatBlock(minHeight, maxHeight);
                region.addBlock(x, y, index, block);
            } else if (type == 0x0040) { //type id from rebellion
                // 64x short, complex block
                Cell[] cells = new Cell[64];
                for (int cell = 0; cell < 64; cell++) {
                    int value = byteBuffer.getShort();

                    short height = (short) ((short) (value & 0x0fff0) >> 1);
                    byte direction = (byte) (value & 0x0F);
                    cells[cell] = new Cell(direction, height);
                }

                Block block = new ComplexBlock(cells);
                region.addBlock(x, y, index, block);
            } else { //0x0048 block id
                // 64x-8192x short, multilevel block
                short[][] height = new short[64][];
                byte[][] NSWE = new byte[64][];
                byte[] layersa = new byte[64];
                for (int cell = 0; cell < 64; cell++) {
                    short layers = byteBuffer.getShort();

                    height[cell] = new short[layers];
                    NSWE[cell] = new byte[layers];
                    for (int i = 0; i < layers; i++) {
                        int value = byteBuffer.getShort();
                        height[cell][i] = (short) ((short) (value & 0x0fff0) >> 1);
                        NSWE[cell][i] = (byte) (value & 0x0F);
                    }
                    layersa[cell] = (byte) --layers;
                }

                Block block = new MultilevelBlock(layersa, height, NSWE);
                region.addBlock(x, y, index, block);
            }

            index++;
        }
    }
}
