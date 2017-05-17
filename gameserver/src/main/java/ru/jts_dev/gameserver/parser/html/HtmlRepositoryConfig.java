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

package ru.jts_dev.gameserver.parser.html;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Java-man
 * @since 04.01.2016
 */
@Configuration
@ConfigurationProperties(prefix = "gameserver.html.repository")
public class HtmlRepositoryConfig {
    private HtmlRepositoryType type;

    public HtmlRepositoryType getType() {
        return type;
    }

    public void setType(HtmlRepositoryType type) {
        this.type = type;
    }

    @Bean
    public HtmlCompressor htmlCompressor() {
        HtmlCompressor htmlCompressor = new HtmlCompressor();

        htmlCompressor.setEnabled(true);                   //if false all compression is off (default is true)
        htmlCompressor.setRemoveComments(true);            //if false keeps HTML comments (default is true)
        htmlCompressor.setRemoveMultiSpaces(true);         //if false keeps multiple whitespace characters (default is true)
        htmlCompressor.setRemoveIntertagSpaces(true);      //removes iter-tag whitespace characters
        htmlCompressor.setRemoveQuotes(true);              //removes unnecessary tag attribute quotes
        htmlCompressor.setSimpleDoctype(false);             //simplify existing doctype
        htmlCompressor.setRemoveScriptAttributes(false);    //remove optional attributes from script tags
        htmlCompressor.setRemoveStyleAttributes(false);     //remove optional attributes from style tags
        htmlCompressor.setRemoveLinkAttributes(true);      //remove optional attributes from link tags
        htmlCompressor.setRemoveFormAttributes(true);      //remove optional attributes from form tags
        htmlCompressor.setRemoveInputAttributes(true);     //remove optional attributes from input tags
        htmlCompressor.setSimpleBooleanAttributes(true);   //remove values from boolean tag attributes
        htmlCompressor.setRemoveJavaScriptProtocol(false);  //remove "javascript:" from inline event handlers
        htmlCompressor.setRemoveHttpProtocol(false);        //replace "http://" with "//" inside tag attributes
        htmlCompressor.setRemoveHttpsProtocol(false);       //replace "https://" with "//" inside tag attributes
        htmlCompressor.setPreserveLineBreaks(false);        //preserves original line breaks
        htmlCompressor.setRemoveSurroundingSpaces("all"); //remove spaces around provided tags

        htmlCompressor.setCompressCss(false);               //compress inline css
        htmlCompressor.setCompressJavaScript(false);        //compress inline javascript

        htmlCompressor.setGenerateStatistics(true);

        return htmlCompressor;
    }
}
