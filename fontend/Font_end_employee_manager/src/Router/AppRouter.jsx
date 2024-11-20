import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Home from "../components/Home";
import Login from "../components/Login";
import Header from "../Header";
import About from "../components/About";
import Contact from "../components/Contact";

export default function App(){
   return(
    <Router>
        <Header/>
        <div className="content">
        <Routes>
            <Route path="/" element={<Login/>}/>
            <Route path="/About" element={<About/>}/>
            <Route path="/home" element={<Home/>}/>
            <Route path="/contact" element={<Contact/>}/>
        </Routes>
        </div>
    </Router>
   );

}