( function(){
	var app = angular.module('taskList', [ ]);

    app.controller('TasksCtrl', ['$http', function($http){

    this.content = ""
    store = this;
    store.tasks = [];

    url = "http://localhost:8080/task"

    this.getTasks = function(){
        $http.get(url).success(function(data){
                        			store.tasks = data;
                        		});
    }



    this.addTask = function(content){
        $http.post(url, content).success(function(data){
            store.tasks.push(data);
        });
        this.content = "";
    }

    this.delTask = function(id){
        urlWithId = url+"/"+id
                $http.delete(urlWithId).success(function(data){

                for (i=0; i<store.tasks.length; i++){
                    if(id == store.tasks[i].id){
                        store.tasks.splice(i, 1);
                    }
                }

        });
    }

    this.changeTaskStatus = function(id, status){
        urlWithId = url+"/"+id
        $http.put(urlWithId, status).success(function(data){
            for (i=0; i<store.tasks.length; i++){
                if(id == store.tasks[i].id){
                    store.tasks[i].status = data.status;
                }
            }
        })
    };

    }])
})();