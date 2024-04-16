Projections:
https://stackoverflow.com/questions/22007341/spring-jpa-selecting-specific-columns


Adjacency model in spring data jpa: https://stackoverflow.com/questions/69665513/using-spring-data-jpa-with-an-adjacency-matrix-stored-in-a-mysql-database

Design for comment and category: https://stackoverflow.com/questions/56358596/unable-to-fully-recurse-tree-hierarchy-using-java


Change the pub private key
https://stackoverflow.com/questions/19303584/spring-security-preauthorization-pass-enums-in-directly

https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/

n+1 query problem : https://vladmihalcea.com/n-plus-1-query-problem/

AUthentication failure: 401
Access Denied 403

Handling exceptions in spring security excellent tutorial: https://www.youtube.com/watch?v=sCYoQIBND6w

Psswrod reset: https://www.youtube.com/watch?v=mxs_00KpUE4


COnverter to add custom attributes to authority : https://stackoverflow.com/questions/76702695/spring-security-preauthorize-role-does-not-pick-up-jwt-claim
https://stackoverflow.com/questions/65518172/spring-security-cant-extract-roles-from-jwt

For sorting and pagination
https://www.baeldung.com/spring-data-jpa-pagination-sorting

For comments:
https://codesandbox.io/p/github/swastikpatro/nested-comments/main?file=%2Fsrc%2FApp.tsx%3A3%2C18



#### JPA's cascade = REMOVE and Hibernate's @OnDelete used together?
Let's say you have a one-to-one directional relationship
```java
class House {

    @OneToOne
    Object door;

}
```
If you use CascadeType.REMOVE then deleting the house will also delete the door.
```java
    @OneToOne(cascade=CascadeType.REMOVE)
    Object door;
```
If you use @OnDelete then deleting the door will also delete the house.
```java
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    Object door;
```

### JPA and Hibernate
FindByID antiPattern: https://vladmihalcea.com/spring-data-jpa-findbyid/
The differences between Spring Data JPA’s save, saveAndFlush and saveAll methods: https://thorben-janssen.com/spring-data-jpa-save-saveandflush-and-saveall/
Entity Lifecycle Model in JPA & Hibernate: https://thorben-janssen.com/entity-lifecycle-model/



### Project Documentation:
voteType = 1 -> upvote
voteType = 2 -> downvote

### DONE
1. Completed Auth end to end including jwt token and 
1. Implemented  Blog fetching
2. Implemented Blog CRUD
3. Implemented blog like and dislike

## TODO
1. Integrate comments in frontend and backend
2. Check docker persistence for deployign application
3. Implement view count logic
4. Implement sorting in blog fetching 
5. Search functionality - elastic search
3. Check for jmeter to test application


