import React from 'react';
import './Product.css';  // Add product-specific styles here

const ProductList = ({ products }) => {
    return (
        <div className="product-list">
            {products.map((product) => (
                <div key={product.id} className="product-card">
                    <img
                        src={product.imageUrl}
                        alt={product.name}
                        className="product-image"
                    />
                    <div className="product-details">
                        <h2>{product.name}</h2>
                        <p>Price: ${product.price}</p>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default ProductList;
