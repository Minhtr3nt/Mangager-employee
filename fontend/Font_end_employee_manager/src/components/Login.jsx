import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getToken, setToken } from "../../services/localStorageService";

export default function Login(){
    const [username, setUsername] = useState("");
    const [password,setPassword] = useState("");
    const navigate = useNavigate();
     const removeToken = () => {
        localStorage.removeItem("accessToken");
    };
   useEffect(()=>{
            removeToken();
            const accessToken = getToken();

            if(accessToken){
                navigate("/home");
            }
    },[navigate]);
    const  handleSubmit = (e)=>{
        e.preventDefault();
        removeToken();
        async function fetchLogin(){
            
             await fetch("http://localhost:8080/identity/auth/token",{
                method: "POST",
                headers:{
                    "Content-Type": "application/json", // Để set cái dữ liệu gửi đi là dạng JSON
                },
                body: JSON.stringify({
                    username: username,
                    password: password,
                }),
                
            })
            .then((response)=>{
                    return response.json();
             })
            .then((data)=>{
                 console.log("Response body:", data);

                if(data.code !== 1000){
                    throw new Error(data.message);
                }
               setToken(data.result?.token);
                navigate("/home");
                    
                        
                    
           })
            .catch((error)=>{
                alert(error.message);
            })
            ;
        
        };
        fetchLogin();
        
    }
    
    return (
        
        <div className="login-container">
            <h1>Đăng nhập tài khoản </h1>
            <form>
                <div>
                <label htmlFor="">User name: </label>
                <input id="username" name="username" type="text" value={username} onChange={(e) => {
                    setUsername(e.target.value);
                }}></input>
                </div> 
                <br />
                <div>
                <label htmlFor="">Pass word: </label>
                <input id="password" name="password" type="password" value={password} onChange={(e) => {
                    setPassword(e.target.value);
                }}></input>
                </div> 
                <br />
                <button onClick={handleSubmit}>Đăng nhập</button>
            </form>
        </div>
    );

}