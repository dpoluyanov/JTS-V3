grammar Settings;

import Lang;

file : initial_equipment initial_custom_equipment initial_start_point .*? minimum_stat maximum_stat recommended_stat .*?;

initial_equipment :
    'initial_equipment_begin'
    human_fighter_equipment
    human_magican_equipment
    elf_fighter_equipment
    elf_magican_equipment
    darkelf_fighter_equipment
    darkelf_magican_equipment
    orc_fighter_equipment
    orc_shaman_equipment
    dwarf_apprentice_equipment
    kamael_m_soldier_equipment
    kamael_f_soldier_equipment
    'initial_equipment_end'
    ;

initial_custom_equipment :
    'initial_custom_equipment_begin'
    human_fighter_equipment
    human_magican_equipment
    elf_fighter_equipment
    elf_magican_equipment
    darkelf_fighter_equipment
    darkelf_magican_equipment
    orc_fighter_equipment
    orc_shaman_equipment
    dwarf_apprentice_equipment
    kamael_m_soldier_equipment
    kamael_f_soldier_equipment
    'initial_custom_equipment_end'
    ;

human_fighter_equipment : HUMAN_FIGHTER '=' equipment_array;
human_magican_equipment : HUMAN_MAGICAN '=' equipment_array;
elf_fighter_equipment : ELF_FIGHTER '=' equipment_array;
elf_magican_equipment : ELF_MAGICAN'=' equipment_array;
darkelf_fighter_equipment : DARKELF_FIGHTER '=' equipment_array;
darkelf_magican_equipment : DARKELF_MAGICAN'=' equipment_array;
orc_fighter_equipment : ORC_FIGHTER '=' equipment_array;
orc_shaman_equipment : ORC_SHAMAN '=' equipment_array;
dwarf_apprentice_equipment : DWARP_APPRENTICE '=' equipment_array;
kamael_m_soldier_equipment : KAMAEL_M_SOLDIER '=' equipment_array;
kamael_f_soldier_equipment : KAMAEL_F_SOLDIER '=' equipment_array;

equipment_array: '{' equipment (';' equipment) *'}';
equipment: '{' name_object ';' int_object '}';

initial_start_point :
    'initial_start_point_begin'
    start_point+
    'initial_start_point_end'
    ;

start_point :
    'point_begin'
    point+
    class_
    'point_end'
    ;

point : 'point' '=' vector3D_object;

class_ : 'class' '=' identifier_list;

// todo remove this cheat
identifier_object returns [String value]
    @after {$ctx.value = $text;}
    : HUMAN_FIGHTER | HUMAN_MAGICAN | ELF_FIGHTER | ELF_MAGICAN | DARKELF_FIGHTER | DARKELF_MAGICAN
    | DWARP_APPRENTICE | ORC_FIGHTER | ORC_SHAMAN | KAMAEL_M_SOLDIER | KAMAEL_F_SOLDIER
    ;

minimum_stat :
    'minimum_stat_begin'
    human_fighter_stat
    human_magician_stat
    elf_fighter_stat
    elf_magician_stat
    darkelf_fighter_stat
    darkelf_magician_stat
    orc_fighter_stat
    orc_shaman_stat
    dwarf_apprentice_stat
    kamael_m_soldier_stat
    kamael_f_soldier_stat
    'minimum_stat_end'
    ;

maximum_stat :
    'maximum_stat_begin'
    human_fighter_stat
    human_magician_stat
    elf_fighter_stat
    elf_magician_stat
    darkelf_fighter_stat
    darkelf_magician_stat
    orc_fighter_stat
    orc_shaman_stat
    dwarf_apprentice_stat
    kamael_m_soldier_stat
    kamael_f_soldier_stat
    'maximum_stat_end'
    ;

recommended_stat :
    'recommended_stat_begin'
    human_fighter_stat
    human_magician_stat
    elf_fighter_stat
    elf_magician_stat
    darkelf_fighter_stat
    darkelf_magician_stat
    orc_fighter_stat
    orc_shaman_stat
    dwarf_apprentice_stat
    kamael_m_soldier_stat
    kamael_f_soldier_stat
    'recommended_stat_end'
    ;

human_fighter_stat : HUMAN_FIGHTER '=' int_list;
human_magician_stat : HUMAN_MAGICAN '=' int_list;
elf_fighter_stat : ELF_FIGHTER '=' int_list;
elf_magician_stat : ELF_MAGICAN'=' int_list;
darkelf_fighter_stat : DARKELF_FIGHTER '=' int_list;
darkelf_magician_stat : DARKELF_MAGICAN'=' int_list;
orc_fighter_stat : ORC_FIGHTER'=' int_list;
orc_shaman_stat : ORC_SHAMAN '=' int_list;
dwarf_apprentice_stat : DWARP_APPRENTICE '=' int_list;
kamael_m_soldier_stat : KAMAEL_M_SOLDIER '=' int_list;
kamael_f_soldier_stat : KAMAEL_F_SOLDIER '=' int_list;

HUMAN_FIGHTER : 'human_fighter';
HUMAN_MAGICAN : 'human_magician';
ELF_FIGHTER : 'elf_fighter';
ELF_MAGICAN: 'elf_magician';
DARKELF_FIGHTER: 'darkelf_fighter';
DARKELF_MAGICAN: 'darkelf_magician';
ORC_FIGHTER: 'orc_fighter';
ORC_SHAMAN: 'orc_shaman';
DWARP_APPRENTICE: 'dwarf_apprentice';
KAMAEL_M_SOLDIER : 'kamael_m_soldier';
KAMAEL_F_SOLDIER : 'kamael_f_soldier';
