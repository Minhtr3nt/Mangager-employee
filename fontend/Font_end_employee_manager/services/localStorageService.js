export const KEY_TOKEN = "accessToken"
// mục đích lưu token vào local storage với key và giá trị của token
export const setToken =(token) =>{
    localStorage.setItem(KEY_TOKEN, token);
}

export const getToken =()=>{
    return localStorage.getItem(KEY_TOKEN);
}

export const removeToken = ()=>{
    return localStorage.removeItem(KEY_TOKEN);
}