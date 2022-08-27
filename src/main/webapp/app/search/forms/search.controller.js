(function() {
    'use strict';

    angular
        .module('e2EApp')
        .controller('SearchController', SearchController);

    SearchController.$inject = ['$scope','$state', '$http'];

    function SearchController ($scope, $state, $http) {
        var vm = this;

        vm.doSearch = function(term, sort) {
			$http({
				method: 'GET',
				url: "http://localhost:8080/api/search",
				params: {
				    query: vm.search.searchTerm,
				    sort: typeof sort === 'undefined' ? '' : sort
				}
			}).then(function successCallback(response) {
				if (response.status == 200) {
					vm.search.users = response.data
				}
			});
		};

		vm.doClear = function() {
		    vm.search.searchTerm = '';
		    vm.search.users = [];
		};
    }
})();
