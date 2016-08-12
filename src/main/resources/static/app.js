( function(){
	var app = angular.module('taskList', [ ]);

    app.controller('TasksCtrl', ['$http', function($http){

    this.content = ""
    store = this;
    store.tasks = [];


    this.getTasks = function(){
        url = "http://localhost:8080/tasks"
        $http.get(url).success(function(data){
                        			store.tasks = data;
                        		});
    }



    this.addTask = function(content){
        url = "http://localhost:8080/addtask"
        $http.post(url, content).success(function(data){
            store.tasks.push(data);
        });
        this.content = "";
    }

    this.delTask = function(id){
        url = "http://localhost:8080/deltask/"+id
                $http.delete(url).success(function(data){

                for (i=0; i<store.tasks.length; i++){
                    if(id == store.tasks[i].id){
                        store.tasks.splice(i, 1);
                    }
                }

        });
    }

    this.getTasks();

    }])
})();