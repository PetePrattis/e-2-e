(function() {
    'use strict';

    angular
        .module('e2EApp')
        .controller('SearchController', SearchController);

    SearchController.$inject = ['$scope','$state', '$http'];

    function SearchController ($scope, $state, $http) {
        var vm = this;

        vm.doSearch = function(term, sort) {

            let order = "asc";

            if(typeof vm.search.users !== 'undefined') {
                if(vm.search.users.length > 1 && typeof sort !== 'undefined') {
                    if(vm.search.users[0][sort] < vm.search.users[vm.search.users.length - 1][sort]) {
                        order = "desc";
                    }
                 }
            }



			$http({
				method: 'GET',
				url: "http://localhost:8080/api/search",
				params: {
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
