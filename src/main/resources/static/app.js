(function () {
    var app = angular.module('hrp', [])
        .filter('titleCase', toTitleCase)
        .filter('pluralize', pluralize);

  function pluralize() {
    return function (word, count) {
      var isSingularItem = count == 1 || count == -1;
      if (isSingularItem) {
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

  app.controller('PantryCtrl', ['$http', function ($http) {

    store = this;
    this.newItem = {'unit': 'UNKNOWN'};
    this.content = [];
    this.pantryUrl = "/pantry/item";
    this.productUrl = "/product";

    this.getItems = function () {
      $http.get(this.pantryUrl).success(function (data) {
        store.content = data;
      });
    };

    this.getProductData = function (eanCode) {
      $http.get(this.productUrl + "/" + eanCode).success(function (data) {
        if (!store.newItem.name && store.newItem.unit === 'UNKNOWN') {
          store.newItem.name = data.name;
          store.newItem.unit = data.unit || 'UNKNOWN';
        }
      });
    };

    this.addItem = function () {
      $http.post(this.pantryUrl, this.newItem).success(function (data) {
        store.content.push(data);
      });
      this.newItem = {'unit': 'UNKNOWN'}
    };

    this.removeItem = function (item) {
      uuid = item.uuid;
      urlWithId = this.pantryUrl + "/" + uuid;
      $http.delete(urlWithId).success(removeItemFromStore)
    };

    removeItemFromStore = function () {
      for (i = 0; i < store.content.length; i++) {
        if (uuid == store.content[i].uuid) {
          store.content.splice(i, 1);
        }
      }
    };



  }]);


  app.controller('TasksController', ['$http', function ($http) {

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
      var uuid = task.uuid;
      urlWithId = this.url + "/" + uuid;
      $http.delete(urlWithId).success(removeTaskFromStore);
    };

    removeTaskFromStore = function () {
      for (i = 0; i < store.tasks.length; i++) {
        if (uuid == store.tasks[i].uuid) {
          store.tasks.splice(i, 1);
        }
      }
    };

    this.changeTaskStatus = function (task, status) {
      uuid = task.uuid;
      task.status = status;
      urlWithId = this.url + "/" + uuid;
      $http.patch(urlWithId, task).success(updateTaskStatusInStore)
    };

    updateTaskStatusInStore = function (data) {
      for (i = 0; i < store.tasks.length; i++) {
        if (uuid == store.tasks[i].uuid) {
          store.tasks[i].status = data.status;
        }
      }
    }
  }]);
})();