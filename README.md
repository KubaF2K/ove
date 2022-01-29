# Projekt zaliczeniowy z przedmiotu: _**Progrmowanie obiektowe 2**_

# Temat projektu: Gra z wykorzystaniem bazy danych
## Skład grupy: Jakub Foltarz, Kamil Kondziołka
## Specyfikacja projektu
### Cel projektu : Stworzenie aplikacji z wykorzystaniem bazy danych
#### Cele szczegółowe:
   1. Wykorzystać możliwości JavaFX
   2. Wykorzystać możliwości Hibernate
### Funkcjonalności:
   1. Możliwość dodawania własnych przeciwników, przedmiotów i elementów do gry
   2. Możliwość edycji domyślnych rekordów lub ich usunięcia
   3. Prezentacja możliwości stworzenia gry przy użyciu JavaFX
   4. Możliwość obserwacji działania połączenia javy z bazą danych
### Interfejs:

   <details>
       <summary>Ekran główny</summary>
           Ekran główny zawiera 3 przyciski, służące do przejścia na ekran gry, ekran służący do edytowania oraz wyjście z aplikacji
	
![mainScreen](https://i.imgur.com/OeJ5abB.png)
   </details>
   <details>
       <summary>Ekran gry</summary>
           Ekran gry zawiera informacje o wykonanych turach, przedmiocie aktualnie trzymanym przez gracza, główną planszę, oraz pasek z przedmiotami
	
![gameScreen](https://i.imgur.com/gtxSOjp.png)
   </details>
   
   <details>
       <summary>Ekran edycji</summary>
           Ekran edycji są to wyświetlane w postaci tabeli dane pobierane z bazy danych z możliwościa dodania, edycji, oraz usunięcia rekordów
	
![editMain](https://i.imgur.com/Mgz1ZNj.png)
![editEditor](https://i.imgur.com/dJOLPBG.png)
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
Wypakowywujemy pobrany plik z releases, uruchamiamy plik jar
