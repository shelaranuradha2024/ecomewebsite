import React, { useState } from 'react';
import { FaShoppingCart } from 'react-icons/fa';  // Correct import
import './Header.css';  // Include the CSS file for styling

const Header = ({ onSearch }) => {
    const [searchTerm, setSearchTerm] = useState('');

    const handleSearchChange = (e) => {
        setSearchTerm(e.target.value);
        onSearch(e.target.value);
    };

    return (
        <header className="header">
            <div className="header-logo">
                <h1>Electronic Store</h1> {/* Store name */}
            </div>
            <div className="header-search">
                <input
                    type="text"
                    placeholder="Search for products..."
                    value={searchTerm}
                    onChange={handleSearchChange}
                />
            </div>
            <div className="header-cart">
                <FaShoppingCart className="cart-icon" /> {/* Cart icon */}
            </div>
        </header>
    );
};

export default Header;
