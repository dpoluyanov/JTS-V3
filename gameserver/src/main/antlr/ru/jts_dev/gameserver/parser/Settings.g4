grammar Settings;

import Lang;

@header {
import ru.jts_dev.gameserver.constants.CharacterClass;
import ru.jts_dev.gameserver.constants.CharacterRace;
import ru.jts_dev.gameserver.parser.data.CharacterStat;
import ru.jts_dev.gameserver.parser.data.CharacterStat.*;
}

file : initial_equipment initial_custom_equipment initial_start_point .*? minimum_stat maximum_stat recommended_stat .*?;

initial_equipment :
    'initial_equipment_begin'
    character_equipment+
    'initial_equipment_end'
    ;

initial_custom_equipment :
    'initial_custom_equipment_begin'
    character_equipment+
    'initial_custom_equipment_end'
    ;

character_equipment : character_race_class '=' equipment_array;

equipment_array: '{' equipment (';' equipment) *'}';
equipment: '{' name_object ';' int_object '}';

initial_start_point :
    'initial_start_point_begin'
    start_point+
    'initial_start_point_end'
    ;

start_point
    returns[List<CharacterClass> klasses, List<Vector3D> points]:
    'point_begin'
    (point {$ctx.points.add($point.value);})+
    classes {$ctx.klasses = $classes.value;}
    'point_end'
    ;

point
    returns[Vector3D value]: 'point' '=' vo=vector3D_object {$ctx.value = $vo.value;};

classes
    returns[List<CharacterClass> value = new ArrayList<>()]:
    'class' '=' '{'
    crc=character_race_class { $ctx.value.add($crc.klass); }
    (';' crc=character_race_class { $ctx.value.add($crc.klass); })* '}';

minimum_stat
    returns[List<CharacterStat> value = new ArrayList<>()]:
    'minimum_stat_begin'
    (cs=character_stat  {$ctx.value.add($cs.value);})+
    'minimum_stat_end'
    ;

maximum_stat
    returns[List<CharacterStat> value = new ArrayList<>()]:
    'maximum_stat_begin'
    (cs=character_stat  {$ctx.value.add($cs.value);})+
    'maximum_stat_end'
    ;

recommended_stat
    returns[List<CharacterStat> value = new ArrayList<>()]:
    'recommended_stat_begin'
    (cs=character_stat  {$ctx.value.add($cs.value);})+
    'recommended_stat_end'
    ;

character_stat
    returns[CharacterStat value]:
    crc=character_race_class '=' il=int_list
    {$ctx.value = new CharacterStat($crc.race, $crc.klass, $il.value);}
    ;

character_race_class
    returns[CharacterRace race, CharacterClass klass]:
    HUMAN_FIGHTER {$ctx.race = CharacterRace.HUMAN; $ctx.klass = CharacterClass.HUMAN_FIGHTER;}
    | HUMAN_MAGICAN {$ctx.race = CharacterRace.HUMAN; $ctx.klass = CharacterClass.HUMAN_MAGICAN;}
    | ELF_FIGHTER {$ctx.race = CharacterRace.ELF; $ctx.klass = CharacterClass.ELF_FIGHTER;}
    | ELF_MAGICAN {$ctx.race = CharacterRace.ELF; $ctx.klass = CharacterClass.ELF_MAGICAN;}
    | DARKELF_FIGHTER {$ctx.race = CharacterRace.DARKELF; $ctx.klass = CharacterClass.DARKELF_FIGHTER;}
    | DARKELF_MAGICAN {$ctx.race = CharacterRace.DARKELF; $ctx.klass = CharacterClass.DARKELF_MAGICAN;}
    | ORC_FIGHTER {$ctx.race = CharacterRace.ORC; $ctx.klass = CharacterClass.ORC_FIGHTER;}
    | ORC_SHAMAN {$ctx.race = CharacterRace.ORC; $ctx.klass = CharacterClass.ORC_SHAMAN;}
    | DWARP_APPRENTICE {$ctx.race = CharacterRace.DWARF; $ctx.klass = CharacterClass.DWARP_APPRENTICE;}
    | KAMAEL_M_SOLDIER {$ctx.race = CharacterRace.KAMAEL; $ctx.klass = CharacterClass.KAMAEL_M_SOLDIER;}
    | KAMAEL_F_SOLDIER {$ctx.race = CharacterRace.KAMAEL; $ctx.klass = CharacterClass.KAMAEL_F_SOLDIER;}
    ;

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
