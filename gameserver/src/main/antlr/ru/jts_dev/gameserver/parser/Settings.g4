grammar Settings;

import Lang;

file : .*? minimum_stat maximum_stat recommended_stat .*?;

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

human_fighter_stat : HUMAN_FIGHTER '=' int_array;
human_magician_stat : HUMAN_MAGICAN '=' int_array;
elf_fighter_stat : ELF_FIGHTER '=' int_array;
elf_magician_stat : ELF_MAGICAN'=' int_array;
darkelf_fighter_stat : DARKELF_FIGHTER '=' int_array;
darkelf_magician_stat : DARKELF_MAGICAN'=' int_array;
orc_fighter_stat : ORC_FIGHTER'=' int_array;
orc_shaman_stat : ORC_SHAMAN '=' int_array;
dwarf_apprentice_stat : DWARP_APPRENTICE '=' int_array;
kamael_m_soldier_stat : KAMAEL_M_SOLDIER '=' int_array;
kamael_f_soldier_stat : KAMAEL_F_SOLDIER '=' int_array;

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
