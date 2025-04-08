// Constants
const API_BASE_URL = 'http://localhost:8080/api';
const MOCK_USER_ID = 'user123';

// DOM Elements
const productsGrid = document.getElementById('products-grid');
const paymentForm = document.getElementById('payment-form');
const viewCartBtn = document.getElementById('view-cart-btn');
const cartModal = document.getElementById('cart-modal');
const cartItems = document.getElementById('cart-items');
const closeModal = document.querySelector('.close');
const cartCount = document.getElementById('cart-count');
const cartTotalAmount = document.getElementById('cart-total-amount');
const toast = document.getElementById('toast');

// Global State
let products = [];
let cart = { userId: MOCK_USER_ID, items: [] };

// Initialize the application
document.addEventListener('DOMContentLoaded', initialize);

function initialize() {
  loadProducts();
  updateCartCount();
  setupEventListeners();
}

function setupEventListeners() {
  // Payment form submission
  paymentForm.addEventListener('submit', handlePaymentSubmission);
  
  // Cart modal
  viewCartBtn.addEventListener('click', openCartModal);
  closeModal.addEventListener('click', closeCartModal);
  window.addEventListener('click', (e) => {
    if (e.target === cartModal) closeCartModal();
  });
}

// API Calls
async function loadProducts() {
  try {
    const response = await fetch(`${API_BASE_URL}/products`);
    if (!response.ok) {
      throw new Error('Failed to fetch products');
    }
    products = await response.json();
    renderProducts();
  } catch (error) {
    console.error('Error loading products:', error);
    // Fallback to mock data
    products = [
      { productId: "1", name: "Laptop Dell", price: 1200.0, image: "https://placehold.co/300x200?text=Laptop+Dell" },
      { productId: "2", name: "iPhone 14", price: 999.0, image: "https://placehold.co/300x200?text=iPhone+14" },
      { productId: "3", name: "Samsung TV", price: 799.0, image: "https://placehold.co/300x200?text=Samsung+TV" },
      { productId: "4", name: "Sony Headphones", price: 349.0, image: "https://placehold.co/300x200?text=Sony+Headphones" }
    ];
    renderProducts();
  }
}

async function loadCart() {
  try {
    const response = await fetch(`${API_BASE_URL}/cart/${MOCK_USER_ID}`);
    if (!response.ok) {
      throw new Error('Failed to fetch cart');
    }
    cart = await response.json();
    updateCartCount();
    return cart;
  } catch (error) {
    console.error('Error loading cart:', error);
    showToast('Failed to load cart', 'error');
    // Fallback to mock data
    cart = {
      userId: MOCK_USER_ID,
      items: [
        { productId: "1", quantity: 1 }
      ]
    };
    updateCartCount();
    return cart;
  }
}

async function addToCart(productId) {
  try {
    const response = await fetch(`${API_BASE_URL}/cart/${MOCK_USER_ID}/add?productId=${productId}&quantity=1`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      }
    });
    
    if (!response.ok) {
      throw new Error('Failed to add item to cart');
    }
    
    const product = products.find(p => p.productId === productId);
    showToast(`${product.name} added to cart!`, 'success');
    
    // Update local cart state
    const existingItem = cart.items.find(item => item.productId === productId);
    if (existingItem) {
      existingItem.quantity += 1;
    } else {
      cart.items.push({ productId, quantity: 1 });
    }
    
    updateCartCount();
  } catch (error) {
    console.error('Error adding to cart:', error);
    showToast('Failed to add item to cart', 'error');
  }
}

async function initiateCheckout(orderId) {
  try {
    const response = await fetch(`${API_BASE_URL}/payments/${orderId}/pay?paymentMethod=VNPay`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      }
    });
    
    if (!response.ok) {
      throw new Error('Failed to initiate payment');
    }
    
    const result = await response.json();
    console.log('Payment initiated:', result);
    showToast('Payment initiated successfully!', 'success');
    // In a real app, we might redirect to a payment gateway here
  } catch (error) {
    console.error('Error initiating payment:', error);
    showToast('Failed to initiate payment', 'error');
  }
}

// Rendering functions
function renderProducts() {
  if (!products.length) {
    productsGrid.innerHTML = '<div class="loading">No products found</div>';
    return;
  }
  
  productsGrid.innerHTML = '';
  products.forEach(product => {
    const productElement = createProductElement(product);
    productsGrid.appendChild(productElement);
  });
}

function createProductElement(product) {
  const productElement = document.createElement('div');
  productElement.className = 'product-card';
  
  productElement.innerHTML = `
    <img src="${product.image}" alt="${product.name}" class="product-image">
    <div class="product-details">
      <h3>${product.name}</h3>
      <div class="product-price">$${product.price.toFixed(2)}</div>
      <button class="add-to-cart" data-product-id="${product.productId}">Add to Cart</button>
    </div>
  `;
  
  const addToCartButton = productElement.querySelector('.add-to-cart');
  addToCartButton.addEventListener('click', () => {
    addToCart(product.productId);
  });
  
  return productElement;
}

function renderCartItems() {
  cartItems.innerHTML = '';
  let total = 0;
  
  if (cart.items.length === 0) {
    cartItems.innerHTML = '<p>Your cart is empty</p>';
    cartTotalAmount.textContent = '$0.00';
    return;
  }
  
  cart.items.forEach(item => {
    const product = products.find(p => p.productId === item.productId);
    if (!product) return;
    
    const itemTotal = product.price * item.quantity;
    total += itemTotal;
    
    const cartItemElement = document.createElement('div');
    cartItemElement.className = 'cart-item';
    cartItemElement.innerHTML = `
      <img src="${product.image}" alt="${product.name}">
      <div class="cart-item-details">
        <h4>${product.name}</h4>
        <p>$${product.price.toFixed(2)} × ${item.quantity}</p>
      </div>
      <span class="cart-item-total">$${itemTotal.toFixed(2)}</span>
    `;
    
    cartItems.appendChild(cartItemElement);
  });
  
  cartTotalAmount.textContent = `$${total.toFixed(2)}`;
}

// Event handlers
async function handlePaymentSubmission(e) {
  e.preventDefault();
  const orderId = document.getElementById('order-id').value.trim();
  
  if (!orderId) {
    showToast('Please enter an order ID', 'error');
    return;
  }
  
  await initiateCheckout(orderId);
}

function openCartModal() {
  loadCart().then(() => {
    renderCartItems();
    cartModal.style.display = 'block';
  });
}

function closeCartModal() {
  cartModal.style.display = 'none';
}

// Utility functions
function updateCartCount() {
  let count = 0;
  cart.items.forEach(item => {
    count += item.quantity;
  });
  cartCount.textContent = count;
}

function showToast(message, type = 'success') {
  toast.textContent = message;
  toast.className = `toast ${type} show`;
  
  setTimeout(() => {
    toast.className = toast.className.replace('show', '');
  }, 3000);
}