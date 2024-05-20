# WineBook
## Bevezető
Ez a JAVA projekt egy szűk határidőre készülő beadandó feladat. 
A backend része JAVA 21-el és Java Spring 3.2.5-el készül REST API-al, MVC
& Repository Patternel, valamint Featureizált felosztással.

### Miért Spring? / Miért REST API?

Nos azért, mert elsősorban Web fejlesztőnek készültem és röviden a határidő
előtt nem kísérletezik az ember. Ezt ismerem, ezzel vállalok kevés kockázatot
hogy időben elkészüljek. Ezen felül moduláris, azaz ha nem tetszik a frontend, 
bármilyen másikat lehet készíteni, a backend működőképes marad.

### MVC | Repository Pattern

Az MVC, az a Model-View-Controller szokásos hármasa. A Model felel az adatért,
A View a megjelenítésért, a Controller pedig összeköti a kettőt.
A Repository Pattern ezt azzal egészíti ki, hogy a fentebb említettekhez
bekerül egy Repository layer is, amit valahová a Model és a Controller layer
közé kell képzelni. Az a dolga, hogy az általában Modelhez besűrített adatbázis
műveleteket (CRUD) átvegye a Modeltől, így annak már csak a valós
adattárolás legyen a dolga. 

***De Miért?***

Hallom már a kérdést. Azért, mert számomra mindig túl zsúfoltak a Modellek,
én nem igazodom ki rajtuk, ez egy személyes preferencia és mivel nem volt 
ezzel kapcsolatban megkötés, így hozzá adtam.

### Featurizált felosztás

Na ez is mit jelent. Azt jelenti, hogy nem a szokásos MVC packegekkel
építem a programok (azaz van egy Controller, egy Model és egy View package).
Ehelyett Feature-ökre (funciókra) bontom a programot és annak a 
funkciónak (pld. user-hez kötődő dolgok, register, login, stb.) egy package-be
pakolom a Controllerjét, a Modeljét, a Repositoryját és a Viewját is.
Ez lehetővé teszi számomra, hogy package private legyek, ezzel is szem előtt
tartva az Enkapszuláció elvét, illetve számomra logikusabb az elrendezése.

## Elindítás

Szükség lesz 
 * Java 21-re: https://www.oracle.com/java/technologies/downloads/#java21
 * PostgresSQL-re: https://www.postgresql.org/download/

A PostgresSQL telepítésénél fontos, hogy a postgres felhasználóhoz az "admin" jelszót válasszuk, ugyanis a buildelt verzió ezzel fut!

SQL CLI-be készítsük el az adatbázist:
```
CREATE DATABASE winebook;
```

Ha ez megvan a a **winebook\src\main\resources** mappában lesz egy **schema.sql**
file. Ezt futtassuk le, ezek az adatbázis táblái.

Ha ezzel készen vagyunk, CLI-ben navigáljunk el abba a mappába, ahová letöltöttük a projektet, azon belül is a target mappába.
Itt pedig futtassuk a 
```
java -jar winebook-0.0.1-SNAPSHOT.jar
```
parancsot.

Ha minden sikerült, el is indult! :)
