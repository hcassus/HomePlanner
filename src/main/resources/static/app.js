( function(){
	var app = angular.module('greet', [ ]);
	
	
	app.controller('greeterCtrl', ['$http', function($http){

	    person = { }

	    this.greet = function(name){
	        store = this
            store.message = ""
            url = "http://localhost:8080/greeting?name="+name

	        $http.get(url).success(function(data){
            			store.message = data.content;
            		});

	    }

        this.formalgreet = function(name){
        	        store = this
                    store.message = ""
                    url = "http://localhost:8080/formalgreeting?name="+name

        	        $http.get(url).success(function(data){
                    			store.message = data.content;
                    		});
        	    }



	}]);
	
})();