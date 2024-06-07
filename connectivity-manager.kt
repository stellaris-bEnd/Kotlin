val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
val networkCallback = object : ConnectivityManager.NetworkCallback() {
    override fun onAvailable(network: Network) {
        // Network is available, trigger sync
        syncData()
    }

    override fun onLost(network: Network) {
        // Network is lost, handle accordingly
    }
}

connectivityManager.registerDefaultNetworkCallback(networkCallback)
