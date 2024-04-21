

SSH to the server
```shell
ssh root@104.236.197.121    
```

### Blogger-Infrastucture-Setup ( to be done at the startup)

1. From local to server
```shell 
scp docker-compose.yaml root@104.236.197.121:/
```

2. copy the docker compose to the server and then run the
```shell
docker compose up -d
```

3. Login to the minio admin console: http://104.236.197.121:9001/login 
4. create new bucket blog and update the access to custom:
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
5. get the access keys for the bucket and update the application.properties file

### backend deployment:
1. The project is build on java 17
2. export JAVA_HOME=`/usr/libexec/java_home -v 17` run this command if needed
3. Run a build locally using. ```mvn clean package``` with the updated properties
4. Refer https://spring.io/guides/topicals/spring-boot-docker
5. On the server run the following commands to deploy the application

```shell
  export JAVA_HOME=`/usr/libexec/java_home -v 17`
  mvn clean package
  docker build  -t blog/backendappv1 .
```


   ```shell
   //github token
   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
   
   //go to demo folder
   cd blogger-backend/demo
   // to build a local image
   docker build  -t blog/backendappv1 .
   // to run the local image
   
    docker run  -it -d --network=host -p 8080:8080 blog/backendappv1 

   
   cd .
   // to list the old images
   
   docker images
   
   // to list all the containers  (docker ps) for running containers
   docker ps -a  
   docker rm  1af15e84ae97 skajdewrfdqwe
   // list images 
   docker images
   docker image rm 1af15e84ae97 
    ```



### frontend deployment:
# React + Vite

For production build
``` JavaScript
//to create a production build)
npm run build

//to serve the production build
serve -s dist -l 4000

// in production serve using pm2 ~/blogger-frontend/client# pm2 serve dist 5173
 pm2 serve dist 5173

#if already running then restart the server 
  pm2 restart  static-page-server-5173 

    
```

// to stop a running docker container
docker stop image_id
// to remove a runnign docker container
docker rm image_id


NGINX etc/nginx/sites-available/default
```shell

 location  /api/ {
        proxy_pass http://localhost:8080/api/; #whatever port your app runs on
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_cache_bypass $http_upgrade;
    }


 location   /minio/ {
        proxy_pass http://localhost:9000/; #whatever port your app runs on
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_cache_bypass $http_upgrade;
   }

 location / {
        proxy_pass http://localhost:5173/; #whatever port your app runs on
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_cache_bypass $http_upgrade;
    }

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

### Docker Compose
https://www.youtube.com/watch?v=gFjpv-nZO0U


Docker networks:
https://medium.com/@kesaralive/diving-deeper-into-docker-networking-with-docker-compose-737e3b8a3c8c#:~:text=Network%20Modes&text=Host%20%3A%20A%20special%20type%20of,on%20the%20same%20host%20machine.


BUGS:






SPRING SYLLABUS:
1. Bean lifecycle and bean scopes 
2. 