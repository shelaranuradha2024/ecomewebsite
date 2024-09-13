import React from 'react';

function ProductList() {
  const products = [
    {
      id: 1,
      name: 'Black Electronic gadgets Earbuds boAt Oppo',
      price: 1299,
      image: `${process.env.PUBLIC_URL}/images/gadget1.jpg`,
    },
    {
      id: 2,
      name: 'Apple-iphone 15 128GB-Pink ',
      price: 69900,
      image: `${process.env.PUBLIC_URL}/images/gadget2.jpg`,
    },
    {
      id: 3,
      name: 'ASUS TUF F15 Gaming Laptop',
      price: 56019,
      image: `${process.env.PUBLIC_URL}/images/gadget3.jpg`,
    },
    {
      id: 4,
      name: 'STRIFF Laptop Stand',
      price: 299,
      image: `${process.env.PUBLIC_URL}/images/gadget4.jpg`,
    },
  ];

  return (
    <main className="product-grid">
      {products.map((product) => (
        <div className="product" key={product.id}>
          <img src={product.image} alt={product.name} />
          <h3>{product.name}</h3>
          <p>Rs {product.price}</p>
        </div>
      ))}
    </main>
  );
}

export default ProductList;
