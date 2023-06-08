var app = angular.module('myapp', ['ngRoute']);
app.config(function ($routeProvider) {
    $routeProvider
        .when('/more', {
            templateUrl: '/templates/More.html'
        })
        .when('/table', {
            templateUrl: '/templates/TablePage.html'
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
app.controller('TableController', function($scope, $http, $location, DataFactory){
    $http.get("/api/inventory")
        .then(function (response) {
            $scope.inventories = response.data;
        });
    $scope.edit = function (object) {
        DataFactory.setData(object);
        $location.path("/more")
        console.log("object " + object.modelName);
        
    }
console.log("hello controller");
});
app.controller('MoreController', function($scope, DataFactory){
    $scope.hello = " hello this is yobanyu angularjs";
    $scope.content = DataFactory.getData();
    $scope.inp = {
        category: $scope.content.category,
        subCategory: $scope.content.subCategory,
        manufacturer: $scope.content.manufacturer,
        modelName: $scope.content.modelName,
        purchasePrice: $scope.content.purchasePrice,
        sellingPrice: $scope.content.sellingPrice,
        quantityInStock: $scope.content.quantityInStock
    }
});
