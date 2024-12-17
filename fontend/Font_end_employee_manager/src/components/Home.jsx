import  { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getToken, removeToken } from "../../services/localStorageService";

export default function Home() {
    const navigate = useNavigate();
    const [userDetails, setUserDetails] = useState({});
    const [user, setUsers] = useState([]);
    const getUserDetails = async (accessToken) => {

        const response = await fetch(
            "http://localhost:8080/identity/users/myInfo",
            {
                method: "GET", 
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                }
            });

        const data = await response.json();
        console.log(data);
        setUserDetails(data.result);
    };
    const getUsers = async (accessToken) => {
        const response = await fetch(
            "http://localhost:8080/identity/users",
            {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                }
            });
        const data = await response.json();
        setUsers(data.result)
    }

    const handleDelete = async (userId, userName) => {
        const accessToken = getToken();
        const confirmDel = window.confirm("Bạn có chắc chắn muốn xóa nhân viên này? ");
        if (confirmDel) {
            const sign = await fetch(`http://localhost:8080/identity/users/${userId}`,
                {
                    method: "DELETE",
                    headers: {
                        Authorization: `Bearer ${accessToken}`,
                    }
                }
            )
            if (sign) {
                alert("Nhân viên " + userName + " đã bị xóa!!")
                navigate("/");
            }
        }
    }



    useEffect(() => {
        const accessToken = getToken();
        if (accessToken) {
            getUserDetails(accessToken);
            getUsers(accessToken);
        } else {
            navigate("/");
        }
    }, [navigate]);

    return (
        <div >
            <h1>Welcom to the Page {userDetails.username}!</h1>
            <h3>Danh sách nhân viên </h3>
            <table>
                <thead>
                    <tr>
                        <th>ID nhân viên</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>User Name</th>
                        <th>Day of Birth</th>

                        <th>Xoá</th>
                    </tr>
                </thead>
                <tbody>
                    {user.map((item) => (
                        <tr key={item.id}>
                            <td>{item.id}</td>
                            <td>{item.firstName}</td>
                            <td>{item.lastName}</td>
                            <td>{item.username}</td>
                            <td>{item.dob}</td>
                            <td><button onClick={() => handleDelete(item.id, item.firstName)}><img className="deleteIcon" src="./deleteIcon.png" alt="" /></button></td>
                        </tr>
                    ))}

                </tbody>
            </table>


            <button onClick={() => {
                removeToken();
                navigate("/")
            }}>Quay lại đăng nhập</button>

        </div>
    )
}