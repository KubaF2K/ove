# Projekt zaliczeniowy z przedmiotu: _**Progrmowanie obiektowe 2**_

# Temat projektu: Gra z wykorzystaniem bazy danych
## Skład grupy: Jakub Foltarz, Kamil Kondziołka
## Specyfikacja projektu
### Cel projektu : Stworzenie aplikacji z wykorzystaniem bazy danych
#### Cele szczegółowe:
   1. Wykorzystac mozliwosci JavaFX
   2. Wykorzystac mozliwosci Hibernate
### Funkcjonalności:
   1. Mozliwosc dodawania wlasnych przeciwnikow, przedmiotow i elementow do gry
   2. Mozliwosc edycji domyslnych rekordow lub ich usuniecia
### Interfejs:

   <details>
       <summary>Ekran główny </summary>
           Ekran główny zawiera 3 przyciski, sluzace do przejscia na ekran gry, ekran sluzacy do edytowania oraz wyjscie z aplikacji
   </details>
	<details>
       <summary>Ekran gry</summary>
           Ekran gry zawiera informacje o wykonanych turach, przedmiocie trzymanym informacji o graczu, glowna plansze, oraz pasek z przedmiotami
   </details>
   <details>
       <summary>Ekran edycji</summary>
           Ekran edycji sa to wyswietlane w postaci tabeli dane pobierane z bazy danych z mozliwacia dodania edycji i usuniecia rekordow
   </details>
         
### Baza danych

####	Diagram ERD
![image](https://i.gyazo.com/54da70ba618fac17c8643c30241f2932.png)

####	Skrypt do utworzenia struktury bazy danych

    create sequence HIBERNATE_SEQUENCE;

    create table ELEMENT
    (
        ID_ELEMENT INTEGER not null
            primary key,
        NAME       VARCHAR(255),
        SPRITE     VARCHAR(255),
        STRONG_TO  INTEGER,
        WEAK_TO    INTEGER
    );

    create table ITEM
    (
        ID_ITEM    INTEGER not null
            primary key,
        DMG_MAX    INTEGER,
        DMG_MIN    INTEGER,
        NAME       VARCHAR(255),
        SPRITE     VARCHAR(255),
        TYPE       INTEGER,
        ID_ELEMENT INTEGER,
        constraint FKLX87NCEXOM3SM7GYOW7JXMCYR
            foreign key (ID_ELEMENT) references ELEMENT (ID_ELEMENT)
    );

    create table ENEMY
    (
        ID_ENEMY   INTEGER not null
            primary key,
        DMG_MAX    INTEGER,
        DMG_MIN    INTEGER,
        HEALTH     INTEGER,
        NAME       VARCHAR(255),
        SPRITE     VARCHAR(255),
        ID_ELEMENT INTEGER,
        ID_ITEM    INTEGER,
        constraint FK7IGDJVH7ND9VLBMCMTI5JWH6A
            foreign key (ID_ITEM) references ITEM (ID_ITEM),
        constraint FKIX7GLU4EK87U7LM2SWUVBHHR5
            foreign key (ID_ELEMENT) references ELEMENT (ID_ELEMENT)
    );


### Wykorzystane technologie:
    1. JavaFX
    2. Hibernate
    3. H2
## Proces uruchomienia aplikacji (krok po kroku)
Wypakowywujemy plik ver1.7z, uruchamiamy evo.jar
