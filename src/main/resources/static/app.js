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

  }]);

  app.controller('PantryCtrl', ['$http', function ($http) {

    this.addItem = function () {
      this.content.push(this.newItem);
      this.newItem = {}
    };

    this.content = [
      {
        'name': 'Coca Cola 2l',
        'quantity': 1,
        'unit': 'BOTTLE',
        'uuid': 'f12123-de123c-a45123-c123-d12e12'
      },
      {
        'name': 'Erdinger 500ml',
        'quantity': 10,
        'unit': 'BOTTLE',
        'uuid': '4dbf39-fb6745-dda544-456b-d211da'
      }
    ];
  }])

})();