# mlcontest-api

## Requirements
- [Java 17](https://openjdk.org/projects/jdk/17/)
- [PostgreSQL](https://www.postgresql.org/download/)
- [Docker](https://www.docker.com/)
- [Maven](https://maven.apache.org/download.cgi)
- A [cloudinary](https://cloudinary.com/) api key for image storing

## Setup

### Clone the repository

```bash
git clone https://github.com/Ola-jed/mlcontest-api
cd mlcontest-api
# Create the database in postgres
```

### Define the environment variables such as expected in the `application.properties`

```bash
# Set the values for the environment variables in your system by using
export CONFIG=value

# The environment variables are the following

# DATA_SOURCE_URL : The url of the database in the jdbc format
# DATASOURCE_USERNAME : The username for Postgres
# DATASOURCE_PASSWORD : The password for the database
# TOKEN_SECRET : The secret key for the jwt token generation
# MAIL_HOST : The smtp host
# MAIL_PORT : The port on the smtp server
# MAIL_USERNAME : The username for the smtp server
# MAIL_PASSWORD : The password for the user on the smtp server
# MAIL_SMTP_AUTH : Auth parameter for the smtp service
# MAIL_SMTP_STARTTLS_ENABLE : Whether tls is enabled for the smtp
# CLOUDINARY_URL : The url used for the cloudinary account
```

### Run the project
 
```bash
# Run using docker : host-mode
docker build -t mlcontest-api .
docker run --network="host" mlcontest-api

# Or, run using the maven wrapper if you have java 17
./mvnw spring-boot:run
```
Once started, the api will be running on the port 8080

Swagger ui is available on the uri [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)


## Deployment
For the deployment, you can use either a service that accept docker image uploading or which support jvm applications.
But before, you should configure an SMTP service, a PostgreSQL database and a cloudinary account

### Deployment using docker ([Render](https://render.com/))
Render is a platform which allow to easily deploy web services.
After creating an account, you can log in to the [dashboard](https://dashboard.render.com/)
and create a new [web service](https://dashboard.render.com/web/new)

![New web service](https://i.imgur.com/7JlWE8W.png)

Once the web service is created, you can link it to an existing GitHub repository

![Link an existing repository](https://i.imgur.com/2kfmmOf.png)

While creating the project, you can also define the secrets introduced in the `Setup` section

![Secrets](https://i.imgur.com/OrdwB2q.png)

The project will be deployed thanks to the provided `Dockerfile` at the repository's root and available online

### Deployment without docker ([heroku](https://www.heroku.com/)) 
Heroku is a cloud platform as a service supporting several programming languages.
The process for deploying Spring boot applications on heroku is very easy.
After creating an account, you can log in to the [dashboard](https://dashboard.heroku.com)
and create a new [app](https://dashboard.heroku.com/new-app)

![New app](https://i.imgur.com/MRGAPCQ.png)

Create the app, then head to the `Deploy` section and configure the GitHub repository or use the heroku cli
which will allow you to deploy from the command line on your computer
![Configure the deployment method](https://i.imgur.com/n7Tg7md.png)

To configure the environment variables, you can go the `Settings` tab and in the ` Config Vars` menu, you should be
able to add environment variables

![Config Vars](https://i.imgur.com/LH0bC04.png)


The project will be deployed every time a push is done on the configured branch

- Sidenote: Because, by default, the heroku runner uses java 8, make sure you have at the root of the repository
a file `system.properties` with the content
```properties
java.runtime.version=17
```