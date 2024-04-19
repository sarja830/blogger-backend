const storeInSession = (key, value) =>{
    // return sessionStorage.setItem(key ,value);
    return localStorage.setItem(key ,value);
}

const lookInSession = (key) =>{
    // return sessionStorage.getItem(key)
    return localStorage.getItem(key)
}

const removeFromSession = (key) =>{
    // return sessionStorage.removeItem(key)
    return localStorage.removeItem(key)
}

const logOutUser = () => {
    // sessionStorage.clear();
    localStorage.clear();
}

export {storeInSession, lookInSession, logOutUser, removeFromSession} 