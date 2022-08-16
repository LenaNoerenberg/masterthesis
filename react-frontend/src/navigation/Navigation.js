import React from "react";
import {
    Nav,
    NavLink,
    Bars,
    NavMenu,
    NavBtn,
    NavBtnLink,
  } from '../components/NavbarElements';
const Navbar = () => {
    return (
        <>
          <Nav>
            {/* <Bars /> */}
      
            <NavMenu>
              <NavLink to='/'>
                Home
              </NavLink>
              <NavLink to='/test'>
                Test
              </NavLink>
             
            </NavMenu>
            <NavBtn>
              <NavBtnLink to='/signin'>Sign In</NavBtnLink>
            </NavBtn>
          </Nav>
        </>
      );
}
export default Navbar