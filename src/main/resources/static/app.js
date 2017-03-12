(function () {
  var app = angular.module('hrp', [])
  .filter('titleCase', toTitleCase)
  .filter('pluralize', pluralize);

  function pluralize() {
    return function (word, count) {
      if (count == 1 || count == -1) {
        return word
      }
      return word + 's'
    }

  }

  function toTitleCase() {
    return function (str) {
      return str
      .toLowerCase()
      .split(' ')
      .map(function (word) {
        return word[0].toUpperCase() + word.substr(1);
      })
      .join(' ');
    }
  }

  app.controller('TasksCtrl', ['$http', function ($http) {

    this.task = {};
    store = this;
    store.tasks = [];

    this.url = "/task";

    this.getTasks = function () {
      $http.get(this.url).success(function (data) {
        store.tasks = data;
      });
    };

    this.addTask = function (task) {
      $http.post(this.url, task).success(function (data) {
        store.tasks.push(data);
      });
      this.task = {};
    };

    this.delTask = function (task) {
      uuid = task.uuid;
      urlWithId = this.url + "/" + uuid;
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
      urlWithId = this.url + "/" + uuid;
      $http.patch(urlWithId, status).success(function (data) {
        for (i = 0; i < store.tasks.length; i++) {
          if (uuid == store.tasks[i].uuid) {
            store.tasks[i].status = data.status;
          }
        }
      })
    };

  }]);

  app.controller('PantryCtrl', ['$http', function ($http) {

    store = this;
    this.url = "/pantry/item";

    this.getItems = function () {
      $http.get(this.url).success(function (data) {
        store.content = data;
      });
    };

    this.addItem = function () {
      $http.post(this.url, this.newItem).success(function (data) {
        store.content.push(data);
      });
      this.newItem = {'unit': 'UNKNOWN'}
    };

    this.removeItem = function (item) {
      uuid = item.uuid;
      urlWithId = this.url + "/" + uuid;
      $http.delete(urlWithId).success(function () {
            for (i = 0; i < store.content.length; i++) {
              if (uuid == store.content[i].uuid) {
                store.content.splice(i, 1);
              }
            }
          }
      )

    };

    this.newItem = {'unit': 'UNKNOWN'};

    this.content = [];
  }])

})();