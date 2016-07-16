grammar PcParameters;

import Lang;

file: .*? pc_collision_box_table .*?;

pc_collision_box_table :
    'pc_collision_box_table_begin'
    collision_stat+
    'pc_collision_box_table_end'
    ;

collision_stat : pc_name '=' double_list;

pc_name : FFIGHTER | MFIGHTER | FMAGIC | MMAGIC | FELF_FIGHTER | MELF_FIGHTER | FELF_MAGIC | MELF_MAGIC
    | FDARKELF_FIGHTER | MDARKELF_FIGHTER | FDARKELF_MAGIC | MDARKELF_MAGIC | FORC_FIGHTER | MORC_FIGHTER
    | FSHAMAN | MSHAMAN | FDWARF_FIGHTER | MDWARF_FIGHTER | FKAMAEL_SOLDIER | MKAMAEL_SOLDIER
    ;

FFIGHTER: 'FFighter';
MFIGHTER: 'MFighter';
FMAGIC: 'FMagic';
MMAGIC: 'MMagic';
FELF_FIGHTER: 'FElfFighter';
MELF_FIGHTER: 'MElfFighter';
FELF_MAGIC: 'FElfMagic';
MELF_MAGIC: 'MElfMagic';
FDARKELF_FIGHTER: 'FDarkelfFighter';
MDARKELF_FIGHTER: 'MDarkelfFighter';
FDARKELF_MAGIC: 'FDarkelfMagic';
MDARKELF_MAGIC: 'MDarkelfMagic';
FORC_FIGHTER: 'FOrcFighter';
MORC_FIGHTER: 'MOrcFighter';
FSHAMAN: 'FShaman';
MSHAMAN: 'MShaman';
FDWARF_FIGHTER: 'FDwarfFighter';
MDWARF_FIGHTER: 'MDwarfFighter';
FKAMAEL_SOLDIER: 'FKamaelSoldier';
MKAMAEL_SOLDIER: 'MKamaelSoldier';