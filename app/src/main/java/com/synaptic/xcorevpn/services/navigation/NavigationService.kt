package com.synaptic.xcorevpn.services.navigation

import androidx.navigation.NavHostController

class NavigationService(val navController: NavHostController) {

    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun navigateTo(route: AppRouts){
        if(currentRoute != route.name){
            navController.navigate(route.name)
        }
    }
    fun pop(){
        navController.popBackStack()
    }
    companion object{
        lateinit var shared: NavigationService
    }
}