# Track-It

Track It is Java based Spring Boot - desktop application that helps you to track your school/university attendance. It is not only capable of maintaining your attendance track but it can also predict the number of classes that you can skip based on the total number of classes, the number of classes you have attended, and the attendance criteria. A settings class is also begin provided to modfiy the look-and-feel and to change the attendance ceriteria. Setting class is structurized using Singelton-Pattern.</br>
Loader class read inputs from TimeTable.txt file, convert those inputs into objects, and store those objects into the database. With each load, a week of data will be stored in the database.</br>
</br>
Dependencies: </br>
1. Spring Data JPA </br>
2. MySQL Driver </br>
3. Lombok