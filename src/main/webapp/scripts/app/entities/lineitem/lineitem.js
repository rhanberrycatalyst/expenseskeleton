'use strict';

angular.module('expensereportApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('lineitem', {
                parent: 'entity',
                url: '/lineitems',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Lineitems'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lineitem/lineitems.html',
                        controller: 'LineitemController'
                    }
                },
                resolve: {
                }
            })
            .state('lineitem.detail', {
                parent: 'entity',
                url: '/lineitem/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Lineitem'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lineitem/lineitem-detail.html',
                        controller: 'LineitemDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Lineitem', function($stateParams, Lineitem) {
                        return Lineitem.get({id : $stateParams.id});
                    }]
                }
            })
            .state('lineitem.new', {
                parent: 'lineitem',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lineitem/lineitem-dialog.html',
                        controller: 'LineitemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    amount: null,
                                    type: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('lineitem', null, { reload: true });
                    }, function() {
                        $state.go('lineitem');
                    })
                }]
            })
            .state('lineitem.edit', {
                parent: 'lineitem',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lineitem/lineitem-dialog.html',
                        controller: 'LineitemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Lineitem', function(Lineitem) {
                                return Lineitem.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('lineitem', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('lineitem.delete', {
                parent: 'lineitem',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lineitem/lineitem-delete-dialog.html',
                        controller: 'LineitemDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Lineitem', function(Lineitem) {
                                return Lineitem.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('lineitem', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
