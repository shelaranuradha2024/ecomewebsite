import React, { useState, useEffect } from 'react';
import Header from './components/Header';
import ProductList from './components/ProductList';

const App = () => {
    const [products, setProducts] = useState([]);

    // Fetch products from the backend
    useEffect(() => {
        fetch('http://localhost:8081/product/')
            .then((response) => response.json())
            .then((data) => setProducts(data))
            .catch((error) => console.error('Error fetching products:', error));
    }, []);

    // Handle search functionality
    const handleSearch = (searchTerm) => {
        fetch(`http://localhost:8081/product/search?name=${searchTerm}`)
            .then((response) => response.json())
            .then((data) => setProducts(data))
            .catch((error) => console.error('Error searching products:', error));
    };

    return (
        <div>
            <Header onSearch={handleSearch} />
            <ProductList products={products} />
        </div>
    );
};

export default App;
