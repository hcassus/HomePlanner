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
        assignDataToStoreContent(data, store.content);
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
      removeContentFromStore(store.content)
    };


  }]);

  app.controller('TasksController', ['$http', function ($http) {

    this.task = {};
    store = this;
    store.tasks = [];

    this.url = "/task";

    this.getTasks = function () {
            $http.get(this.url).success(function (data) {
              assignDataToStoreContent(data, store.tasks)
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
      $http.delete(urlWithId).success(function () {
        removeContentFromStore(store.tasks)
      });
    };

    this.changeTaskStatus = function (task, status) {
      uuid = task.uuid;
      task.status = status;
      urlWithId = this.url + "/" + uuid;
      $http.patch(urlWithId, task).success(function (data) {
        for (i = 0; i < store.tasks.length; i++) {
          if (uuid == store.tasks[i].uuid) {
            store.tasks[i] = data;
          }
        }
      })
    };
  }]);

  app.controller('SignupController', ['$http', function ($http) {

    this.user = {};
    this.registrationSuccess = false;
    this.passwordMismatch = false;
    store = this;

    this.url = "/signup";

    this.signupUser = function (user) {
      store.registrationSuccess = false;
      store.passwordMismatch = false;
      if(user.password != user.passwordConfirmation){
        store.passwordMismatch = true
      } else {
        $http.post(this.url, user).success(function (data) {
          store.registrationSuccess = true;
          this.user = {};
        });
      }
    };
  }

  ]);

})();

function removeContentFromStore(storeContent) {
  for (i = 0; i < storeContent.length; i++) {
    if (uuid == storeContent[i].uuid) {
      storeContent.splice(i, 1);
    }
  }
}

function assignDataToStoreContent(data, storeContent) {
  storeContent = data;
}
