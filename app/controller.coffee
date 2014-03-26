devoxx = angular.module 'devoxx', []

.controller 'DeveloperController', class
    constructor: ($http) ->
      $http.get('/developers').success (data) =>
        @developers = data
