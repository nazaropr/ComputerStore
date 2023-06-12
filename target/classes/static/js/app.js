var app = angular.module('myapp', ['ngRoute']);
app.config(function ($routeProvider) {
    $routeProvider
        .when('/more', {
            templateUrl: '/templates/More.html'
        })
        .when('/table', {
            templateUrl: '/templates/TablePage.html'
        })
        .when('/add', {
            templateUrl: '/templates/AddPage.html'
        })
        .when('/store', {
            templateUrl: '/templates/StorePage.html'
        })
        .otherwise({
            redirectTo: '/table'
        });
});

app.factory('DataFactory', function () {
    var data;
    return {
        setData: function (newData) {
            data = newData;
        },
        getData: function () {
            return data;
        },
    };
})

app.controller('TableController', function($scope, $http, $location, DataFactory, $route, $window, $filter){

    $scope.addNew = function () {
        $location.path("/add")
    }
    $http.get("/api/inventory")
        .then(function (response) {
            $scope.inventories = response.data;
        });
    $scope.edit = function (object) {
        DataFactory.setData(object);
        $location.path("/more")
        console.log("object " + JSON.stringify(object));
        
    }
    $scope.delete = function (object) {
        var confirmed = window.confirm("Are you sure you want to delete this product?");
        if (confirmed){
            console.log("object id" + object.idStr);
            $http.delete("api/inventory/" + object.idStr)
                .then(function (response) {
                    if (response.status === 204) {
                        console.log("response" + response.data)
                        alert("Product delete succesfully")
                        $route.reload();
                    }
            })
                .catch(function (error) {
                    console.log('Error deleting product', error.status, error.data, error.message);
                    alert("Error deleting product")
                });
        }
    }
    $scope.showModal = function (object) {
        var result = $window.prompt(`Enter the quantity of ${object.modelName} to sell`);
        if (result === "" || result > object.quantityInStock || result <= 0 || isNaN(result)){
            $window.alert("incorrectly entered data");
        }else{
            object.quantityInStock -= result;
            let sum_ = result * object.sellingPrice;
            let profit_ = sum_ - result * object.purchasePrice;
            var date = new Date();
            var formatString = 'yyyy-MM-dd';
            var date_ = $filter('date')(date, formatString);
            var sales_ = parseInt(result);
            console.log("sum_ " + sum_);
            console.log("profit_ " + profit_);
            console.log("date_" + date_);
            $http.put("/api/inventory/" + object.idStr, {
                category: object.category,
                subCategory: object.subCategory,
                manufacturer: object.manufacturer,
                modelName: object.modelName,
                purchasePrice: parseInt(object.purchasePrice),
                sellingPrice: parseInt(object.sellingPrice),
                quantityInStock: parseInt(object.quantityInStock)
            })
                .then(function(response) {
                    console.log("Product updated successfully");
                    $http.post("/api/inventory/transaction", {
                        category: object.category,
                        date: date_,
                        sales: sales_,
                        sum: sum_,
                        profit: profit_
                    }).then(function(response) {
                        console.log("Transaction created successfully");
                    })
                        .catch(function(error) {
                            console.log('Error creating transaction', error.status, error.data);
                            alert("Error creating transaction\n" + JSON.stringify(error.data.errors));
                        });
                    alert("Product updated successfully");
                })
                .catch(function(error) {
                    console.log('Error updating product', error.status, error.data);
                    alert("Error updating Product\n" + JSON.stringify(error.data.errors));
                });
        }
    }
    $scope.addProductTo = function(object){
        var result = $window.prompt(`Enter the quantity of ${object.modelName} to add`);
        if (result <= 0 || isNaN(result)) {
            $window.alert("incorrectly entered data");
        }else {
            var quantityInStock_ = parseInt(object.quantityInStock) + parseInt(result);
            $http.put("/api/inventory/" + object.idStr, {
                category: object.category,
                subCategory: object.subCategory,
                manufacturer: object.manufacturer,
                modelName: object.modelName,
                purchasePrice: parseInt(object.purchasePrice),
                sellingPrice: parseInt(object.sellingPrice),
                quantityInStock: parseInt(quantityInStock_)
            })
                .then(function(response) {
                    console.log("Product updated successfully");
                    alert("Product updated successfully");
                    $route.reload();
                })
                .catch(function(error) {
                    console.log('Error updating product', error.status, error.data);
                    alert("Error updating Product\n" + JSON.stringify(error.data.errors));
                });
        }
    }
})
app.controller('MoreController', function($scope, $http, $location, DataFactory){
    $scope.content = DataFactory.getData();
    $scope.inp = {
        idStr: $scope.content.idStr,
        id: $scope.content.id,
        category: $scope.content.category,
        subCategory: $scope.content.subCategory,
        manufacturer: $scope.content.manufacturer,
        modelName: $scope.content.modelName,
        purchasePrice: parseInt($scope.content.purchasePrice),
        sellingPrice: parseInt($scope.content.sellingPrice),
        quantityInStock: parseInt($scope.content.quantityInStock)
    }
    $scope.save = function () {
        $http.put("/api/inventory/" + $scope.inp.idStr, {
            category: $scope.inp.category,
            subCategory: $scope.inp.subCategory,
            manufacturer: $scope.inp.manufacturer,
            modelName: $scope.inp.modelName,
            purchasePrice: parseInt($scope.inp.purchasePrice),
            sellingPrice: parseInt($scope.inp.sellingPrice),
            quantityInStock: parseInt($scope.inp.quantityInStock)
        })
            .then(function(response) {
                console.log("Product updated successfully");
                $location.path('/table');
                alert("Product updated successfully");
            })
            .catch(function(error) {
                console.log('Error updating product', error.status, error.data);
                alert("Error updating Product\n" + JSON.stringify(error.data.errors));
            });
    };
    $scope.btnBack = function () {
        $location.path("/table")
    }
});

app.controller('AddController', function ($scope, $http, $location){
    $scope.add = {
        id: null,
        category: null,
        subCategory: null,
        manufacturer: null,
        modelName: null,
        purchasePrice: null,
        sellingPrice: null,
        quantityInStock: null
    }
    $scope.clearArea = function () {
        $scope.add.id = "",
        $scope.add.category = "",
        $scope.add.subCategory = "",
        $scope.add.manufacturer = "",
        $scope.add.modelName = "",
        $scope.add.purchasePrice = null,
        $scope.add.sellingPrice = null,
        $scope.add.quantityInStock = null
    };
    $scope.addProduct = function () {
        console.log(JSON.stringify($scope.add))
        $http.post("api/inventory",{
            category: $scope.add.category,
            subCategory: $scope.add.subCategory,
            manufacturer: $scope.add.manufacturer,
            modelName: $scope.add.modelName,
            purchasePrice: parseInt($scope.add.purchasePrice),
            sellingPrice: parseInt($scope.add.sellingPrice),
            quantityInStock: parseInt($scope.add.quantityInStock)
        })
            .then(function (response) {
                console.log("Product added successfully ");
                $scope.clearArea();
                alert("Product added successfully");
                $location.path("/table")
            })
            .catch(function (error) {
                console.log('Error adding product', error.status, error.data);
                alert("Error adding product\n" + JSON.stringify(error.data.errors));
            });
    }
    $scope.btnBack = function () {
        $location.path("/table")
    }
})

app.controller('StoreController', function ($scope, $http, $location) {
    $scope.hello = "hello world";
    $http.get("/api/inventory")
        .then(function (response) {
            $scope.inventories = response.data;
        });
})

