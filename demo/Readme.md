

SSH to the server
```shell
ssh root@104.236.197.121    
```

### Blogger-Infrastucture-Setup ( to be done at the startup)

For deployment of resource to remote server just typoe in
1. Dont rerun it run it once during startup

3. From local to server
```shell 
scp docker-compose.yaml root@104.236.197.121:/
```
2. copy the docker compose to the server and then run the
```shell
docker compose up -d
```
3. Login to the minio admin console: http://104.236.197.121:9001/login
   create new bucket blog and update the access to custom:
 get the access keys for the bucket and update the application properties
```
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "PublicRead",
            "Effect": "Allow",
            "Principal": {
                "AWS": [
                    "*"
                ]
            },
            "Action": [
                "s3:GetObject"
            ],
            "Resource": [
                "arn:aws:s3:::blog/*"
            ]
        }
    ]
}
```

change permission for elastic search
chmod -R 777 ./elastic-data
### DEPLOYMENT:

#### Backend:
1. The project is build on java 17
2. export JAVA_HOME=`/usr/libexec/java_home -v 17` run this command if needed
3. Run a build locally using. ```mvn clean package``` with the updated properties
4. Refer https://spring.io/guides/topicals/spring-boot-docker
5. On the server run the following commands to deploy the application

TOKEN: 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

```shell
  export JAVA_HOME=`/usr/libexec/java_home -v 17`
  mvn clean package
  docker build  -t blog/backendappv1 .
```



   ```shell
   // to build a local image
   docker build  -t blog/backendappv1 .
   // to run the local image
  docker run  -it -d --network=host -p 8080:8080 blog/backendappv1 
   // to list the old images
   docker images 
   // delete the old version once the new version has been setup
    docker rmi 1af15e84ae97
    ```

#### frontend:
1. The project is build on node 18.14.0
2. Run the following commands to deploy the frontend
```shell
//for the first time
pm2 --name frontend serve --spa dist 5173


pm2 restart 0
// or 
pm2 restart frontend
````
if you are getting error 
```shell
clone everytime and then start the frontend build.
``` 










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

Passwrod reset: https://www.youtube.com/watch?v=mxs_00KpUE4


COnverter to add custom attributes to authority : https://stackoverflow.com/questions/76702695/spring-security-preauthorize-role-does-not-pick-up-jwt-claim
https://stackoverflow.com/questions/65518172/spring-security-cant-extract-roles-from-jwt

For sorting and pagination
https://www.baeldung.com/spring-data-jpa-pagination-sorting

For comments:
https://codesandbox.io/p/github/swastikpatro/nested-comments/main?file=%2Fsrc%2FApp.tsx%3A3%2C18

Spring Security:
https://engineering.backmarket.com/oauth2-explained-with-cute-shapes-7eae51f20d38

Code in spring
https://medium.com/@iyusubov444/springboot3-oauth2-login-default-config-part-1-c35ca2934818
https://medium.com/@iyusubov444/springboot3-oauth2-login-save-user-info-part-2-f36f5aa5d458

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
4. Logging using actutator 
5. handle delete of elastic search

### Docker Compose
https://www.youtube.com/watch?v=gFjpv-nZO0U


Docker networks:
https://medium.com/@kesaralive/diving-deeper-into-docker-networking-with-docker-compose-737e3b8a3c8c#:~:text=Network%20Modes&text=Host%20%3A%20A%20special%20type%20of,on%20the%20same%20host%20machine.


BUGS:



 docker-compose -f docker-compose-minio.yml -d
```



SPRING SYLLABUS:
1. Bean lifecycle and bean scopes 
2. 