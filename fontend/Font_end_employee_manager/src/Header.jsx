import { Link } from "react-router-dom";
export default function Header(){
    return (
        <header className="header">
            <nav>
                <ul className="menu_header">
                    <li><Link to="/">Login</Link></li>
                    <li><Link to="/About">About</Link></li>
                    <li><Link to="/Contact">Contact</Link></li>
                </ul>
            </nav>
        </header>
    )
}