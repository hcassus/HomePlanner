(function () {
  var app = angular.module('tasklist', []);

  app.controller('TasksCtrl', ['$http', function ($http) {

    this.content = "";
    store = this;
    store.tasks = [];

    url = "/task";

    this.getTasks = function () {
      $http.get(url).success(function (data) {
        store.tasks = data;
      });
    };

    this.addTask = function (content) {
      $http.post(url, content).success(function (data) {
        store.tasks.push(data);
      });
      this.content = "";
    };

    this.delTask = function (task) {
      uuid = task.uuid;
      urlWithId = url + "/" + uuid;
      $http.delete(urlWithId).success(function () {
        for (i = 0; i < store.tasks.length; i++) {
          if (uuid == store.tasks[i].uuid) {
            store.tasks.splice(i, 1);
          }
        }
      });
    };

    this.changeTaskStatus = function (task, status) {
      uuid = task.uuid;
      urlWithId = url + "/" + uuid;
      $http.patch(urlWithId, status).success(function (data) {
        for (i = 0; i < store.tasks.length; i++) {
          if (uuid == store.tasks[i].uuid) {
            store.tasks[i].status = data.status;
          }
        }
      })
    };

  }])
})();