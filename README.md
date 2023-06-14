# Recipe-Management

### Service Definition
This system allows users to manage and organize recipes in a convenient way. Users can create, view, update, and delete recipes, as well as get nutrients for ingredients used in recipes.

#### Swagger

http://localhost:8080/swagger-ui/index.html

### Run Service

#### Pre Requirements

* JDK 17 or higher
* docker and docker Compose
* gradle

### How to execute

```sh
./gradlew build
```

```sh
docker-compose up
```

OR

```sh
./gradlew build
```

```sh
java -jar build/libs/recipies-0.0.1-SNAPSHOT.jar
```


### Usage
Once the application is up and running, you can use the following endpoints to interact with the Recipe Management System:

* GET /recipe: Retrieve a list of all recipes. Optional parameters page and pageSize
* GET /recipe/{id}: Retrieve the details of a specific recipe.
* POST /recipe: Create a new recipe.
* PUT /recipe/{id}: Update the details of a specific recipe.
* DELETE /recipe/{id}: Delete a specific recipe.
* GET /recipe/{id}/nutrients: Get the nutrient value for all the ingredients used in a recipe.