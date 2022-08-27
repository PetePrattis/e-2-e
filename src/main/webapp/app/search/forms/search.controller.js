(function() {
    'use strict';

    angular
        .module('e2EApp')
        .controller('SearchController', SearchController);

    SearchController.$inject = ['$scope','$state', '$http'];

    function SearchController ($scope, $state, $http) {
        var vm = this;

        vm.doSearch = function(term, sort) {

            let order = "asc"; //default order

            if(typeof vm.search.users !== 'undefined') { //if there are results shown
                if(vm.search.users.length > 1 && typeof sort !== 'undefined') { //more than 1 users shown and column clicked to sort by
                    if(vm.search.users[0][sort] < vm.search.users[vm.search.users.length - 1][sort]) { //compare first and last user by user element that also matches column to sort
                        order = "desc";
                    }
                 }
            }

			$http({
				method: 'GET',
				url: "http://localhost:8080/api/search",
				params: { //request with params
				    query: vm.search.searchTerm,
				    sort: typeof sort === 'undefined' ? '' : sort,
				    order: order
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
