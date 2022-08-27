(function() {
    'use strict';

    angular
        .module('e2EApp')
        .controller('SearchController', SearchController);

    SearchController.$inject = ['$scope','$state', '$http'];

    function SearchController ($scope, $state, $http) {
        var vm = this;

        vm.doSearch = function() {
			$http({
				method: 'GET',
				url: "http://localhost:8080/api/search/" + vm.search.searchTerm
			}).then(function successCallback(response) {
				if (response.status == 200) {
					vm.search.users = response.data
				}
			});
		};

		vm.doClear = function() {
		    this.search.searchTerm = '';
		    this.search.users = [];
		};
    }
})();
