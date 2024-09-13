import React from 'react';
import ProductList from './ProductList';

function App() {
  return (
    <div>
      <header>
        <h1>EcomWeb</h1>
        <input type="text" placeholder="Search here..." className="search-bar" />
        <div className="cart-icons">
          <img src={`${process.env.PUBLIC_URL}/images/cart.png`} alt="Cart" />
          <img src={`${process.env.PUBLIC_URL}/images/wishlist.png`} alt="Wishlist" />
        </div>
      </header>

      <ProductList />
    </div>
  );
}

export default App;
