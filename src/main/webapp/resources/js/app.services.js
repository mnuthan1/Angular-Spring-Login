'use strict';

myapp.service('Session', function () {
    this.create = function (data) {
        this.id = data.id;
        this.login = data.login;
        this.firstName = data.firstName;
        this.lastName = data.familyName;
        this.email = data.email;
        this.userRoles = [];
        angular.forEach(data.authorities, function (value, key) {
            this.push(value.name);
        }, this.userRoles);
    };
    this.invalidate = function () {
        this.id = null;
        this.login = null;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.userRoles = null;
    };
    return this;
});


myapp.service('AuthSharedService', function ($rootScope, $http, $resource, authService, Session) {
    return {
        login: function (userName, password, rememberMe) {
            var config = {
                params: {
                    username: userName,
                    password: password,
                    rememberme: rememberMe
                },
                ignoreAuthModule: 'ignoreAuthModule'
            };
            $rootScope.loading = true;
            $http.post('authenticate', '', config)
                .success(function (data, status, headers, config) {
                	$rootScope.loading = false;
                    authService.loginConfirmed(data);
                }).error(function (data, status, headers, config) {
                $rootScope.authenticationError = true;
                Session.invalidate();
            });
        },
        getAccount: function () {
            $rootScope.loadingAccount = true;
            $rootScope.loading = true;
            $http.get('security/account')
            
                .then(function (response) {
                	 $rootScope.loading = false;
                    authService.loginConfirmed(response.data);
                });
        },
        isAuthorized: function (authorizedRoles) {
            if (!angular.isArray(authorizedRoles)) {
                if (authorizedRoles == '*') {
                    return true;
                }
                authorizedRoles = [authorizedRoles];
            }
            var isAuthorized = false;
            angular.forEach(authorizedRoles, function (authorizedRole) {
                var authorized = (!!Session.login &&
                Session.userRoles.indexOf(authorizedRole) !== -1);
                if (authorized || authorizedRole == '*') {
                    isAuthorized = true;
                }
            });
            return isAuthorized;
        },
        logout: function () {
            $rootScope.authenticationError = false;
            $rootScope.authenticated = false;
            $rootScope.account = null;
            $http.get('logout');
            Session.invalidate();
            authService.loginCancelled();
        }
    };
});

myapp.service('HomeService', function ($log, $resource) {
    return {
       
    }
});


myapp.service('UsersService', function ($log, $resource) {
    return {
        getAll: function () {
            var userResource = $resource('users', {}, {
                query: {method: 'GET', params: {}, isArray: true}
            });
            return userResource.query();
        }
    
    }
});


myapp.service('TokensService', function ($log, $resource) {
    return {
        getAll: function () {
            var tokensResource = $resource('security/tokens', {}, {
                query: {method: 'GET', params: {}, isArray: true}
            });
            return tokensResource.query();
        }
    }
});


myapp.service('ItemService', function ($log, $resource) {
    return {
        getAll: function () {
            var itemResource = $resource('items', {}, {
                query: {method: 'GET', params: {}, isArray: true}
            });
            return itemResource.query();
        },
        getByItemNumber: function (search_q) {
        	var search_q = $("#search").val();
        	
            var itemResource = $resource('items/:itemNumber', {itemNumber:search_q}, {
                query: {method: 'GET', params: {}, isArray: true},
               
            });
            return itemResource.query();
        },
        getDetailsByItemNumber: function (item) {
            	var itemResource = $resource('itemdetails/:itemNumber', {itemNumber:item}, {
                query: {method: 'GET', params: {}, isArray: false},
               
            });
            //console.log(JSON.stringify(itemResource));
            return itemResource.query();
        },
        getBoMDetailsByItemNumber : function (item) {
        	var itemResource = $resource('itembomdetails/:itemNumber', {itemNumber:item}, {
        		query: {method: 'GET', params: {}, isArray: true},
        	});
            //console.log(JSON.stringify(itemResource));
            return itemResource.query();
        },
        getWhereUsedDetailsByItemNumber : function(item){
        	var itemResource = $resource('itemwhereuseddetails/:itemNumber', {itemNumber:item}, {
        		query: {method: 'GET', params: {}, isArray: true},
        	});
            //console.log(JSON.stringify(itemResource));
            return itemResource.query();
        },
        getWorkflowHistoryByItemNumber : function(item){
        	var itemResource = $resource('processhistory/:itemNumber', {itemNumber:item}, {
        		query: {method: 'GET', params: {}, isArray: true},
        	});
            //console.log(JSON.stringify(itemResource));
            return itemResource.query();
        }
    }
});

//use these services as resources
myapp.factory('FormDataService', function ($resource) {
    var data = $resource('/form/form-data', {}, {
        startTask: {method:'GET',  params: {processDefinitionId: "@processDefinitionId"}}
    });
    return data;
});


myapp.factory('TasksService', function ($resource) {
    var data = $resource('/runtime/tasks/:taskId', {taskId: "@taskId"},{
        update: {method: 'PUT', params: {taskId: "@taskId"}}
    });
    return data;
});