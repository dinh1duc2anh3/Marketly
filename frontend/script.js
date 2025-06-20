// Constants
const API_BASE_URL = 'http://localhost:8080/api';
const MOCK_USER_ID = 'user123';
const VND_TO_USD = 24000; // Conversion rate for display

// DOM Elements
const productsGrid = document.getElementById('products-grid');
const searchBar = document.getElementById('search-bar');
const categoryBtns = document.querySelectorAll('.category-btn');
const sortDropdown = document.getElementById('sort-dropdown');
const viewCartBtn = document.getElementById('view-cart-btn');
//
const loginModal = document.getElementById('login-modal');
const registerModal = document.getElementById('register-modal');
const cartModal = document.getElementById('cart-modal');
const cartItems = document.getElementById('cart-items');
const closeModal = document.querySelector('.close');
const cartCount = document.getElementById('cart-count');
const cartSubtotal = document.getElementById('cart-subtotal');
const cartVat = document.getElementById('cart-vat');
//
const authButtons = document.getElementById('auth-buttons');
const userMenu = document.getElementById('user-menu');
const deliveryFeeElement = document.getElementById('delivery-fee');
const cartTotalAmount = document.getElementById('cart-total-amount');
const orderForm = document.getElementById('order-form');
const deliveryOptions = document.querySelectorAll('.delivery-option');
const rushDeliveryInfo = document.getElementById('rush-delivery-info');
const deliveryProvinceSelect = document.getElementById('delivery-province');
const toast = document.getElementById('toast');

// Global State
let allProducts = [];
let filteredProducts = [];
let cart = { userId: MOCK_USER_ID, items: [] };
let currentCategory = 'all';
let currentSort = 'default';
let deliveryMethod = 'standard';

// Mock Product Data
const mockProducts = [
  {
    productId: "1",
    name: "iPhone 15 Pro Max",
    category: "electronics",
    price: 29999000,
    image: "https://images.unsplash.com/photo-1592750475338-74b7b21085ab?w=400&q=80",
    rating: 4.8,
    description: "Latest iPhone with advanced camera system"
  },
  {
    productId: "2",
    name: "Samsung Galaxy Book",
    category: "electronics",
    price: 19999000,
    image: "https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=400&q=80",
    rating: 4.6,
    description: "Powerful laptop for work and gaming"
  },
  {
    productId: "3",
    name: "The Psychology of Money",
    category: "books",
    price: 299000,
    image: "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=400&q=80",
    rating: 4.9,
    description: "Best-selling finance book"
  },
  {
    productId: "4",
    name: "Air Fryer Deluxe",
    category: "household",
    price: 2499000,
    image: "https://media.istockphoto.com/id/2213043259/vi/anh/new-modern-air-fryer-appliance-is-on-the-nice-table-in-the-kitchen-of-beautiful-home.jpg?s=2048x2048&w=is&k=20&c=-gvJdRrl_DfgVc7uf3chKHblNG2Rm7tT8NLTdnsHkVc=",
    rating: 4.7,
    description: "Healthy cooking made easy"
  },
  {
    productId: "5",
    name: "Baby Stroller Premium",
    category: "babies",
    price: 4999000,
    image: "https://images.unsplash.com/photo-1544717302-de2939b7ef71?w=400&q=80",
    rating: 4.5,
    description: "Safe and comfortable stroller"
  },
  {
    productId: "6",
    name: "Nike Air Max 270",
    category: "clothing",
    price: 3299000,
    image: "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400&q=80",
    rating: 4.4,
    description: "Comfortable running shoes"
  },
  {
    productId: "7",
    name: "Yoga Mat Premium",
    category: "sports",
    price: 799000,
    image: "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?w=400&q=80",
    rating: 4.6,
    description: "Non-slip yoga mat for all levels"
  },
  {
    productId: "8",
    name: "Skincare Set Deluxe",
    category: "beauty",
    price: 1899000,
    image: "https://media.istockphoto.com/id/1249579132/vi/anh/s%E1%BA%A3n-ph%E1%BA%A9m-l%C3%A0m-%C4%91%E1%BA%B9p-%C4%91%C6%B0%E1%BB%A3c-c%C3%A1ch-ly-tr%C3%AAn-n%E1%BB%81n-tr%E1%BA%AFng.jpg?s=2048x2048&w=is&k=20&c=0KOU0tksjNJD901T182zLZN85mnyu0QbEM-CY7m3iHA=",
    rating: 4.8,
    description: "Complete skincare routine"
  }
];

// Initialize
document.addEventListener('DOMContentLoaded', initialize);

function initialize() {
  loadProducts();
  updateCartCount();
  setupEventListeners();
}

function setupEventListeners() {
  // Authentication
  document.getElementById('login-btn').addEventListener('click', () => openModal('login'));
  document.getElementById('register-btn').addEventListener('click', () => openModal('register'));
  document.getElementById('switch-to-register').addEventListener('click', () => switchModal('register'));
  document.getElementById('switch-to-login').addEventListener('click', () => switchModal('login'));
  document.getElementById('logout-btn').addEventListener('click', logout);

  // Modal controls
  document.getElementById('close-login').addEventListener('click', () => closeModal('login'));
  document.getElementById('close-register').addEventListener('click', () => closeModal('register'));
  document.getElementById('close-cart').addEventListener('click', () => closeModal('cart'));

  // Forms
  document.getElementById('loginForm').addEventListener('submit', handleLogin);
  document.getElementById('registerForm').addEventListener('submit', handleRegister);

   // User menu dropdown
  document.getElementById('user-btn').addEventListener('click', toggleUserDropdown);

  // Search functionality
  searchBar.addEventListener('input', handleSearch);
  
  // Category filtering
  categoryBtns.forEach(btn => {
    btn.addEventListener('click', handleCategoryFilter);
  });
  
  // Sorting
  sortDropdown.addEventListener('change', handleSort);
  
  // Cart modal
  // viewCartBtn.addEventListener('click', openCartModal);
  // closeModal.addEventListener('click', closeCartModal);
  // Cart
  document.getElementById('view-cart-btn').addEventListener('click', () => openModal('cart'));

  // window.addEventListener('click', (e) => {
  //   if (e.target === cartModal) closeCartModal();
  // });

  // Navigation
  document.getElementById('manager-link').addEventListener('click', () => {
    window.location.href = '#manager-dashboard';
  });

  // Close modals when clicking outside
  window.addEventListener('click', function(e) {
    if (e.target.classList.contains('modal')) {
      e.target.style.display = 'none';
    }
  });
  
  // Order form
  orderForm.addEventListener('submit', handleOrderSubmission);
  
  // Delivery options
  deliveryOptions.forEach(option => {
    option.addEventListener('click', handleDeliveryOptionChange);
  });
  
  // Province change for delivery fee calculation
  deliveryProvinceSelect.addEventListener('change', calculateDeliveryFee);
}

// Product Management
async function loadProducts() {
  try {
    const response = await fetch(`${API_BASE_URL}/products`);
    if (!response.ok) {
      throw new Error('Failed to fetch products');
    }
    const data = await response.json();
    allProducts = data;
    filteredProducts = allProducts;
    renderProducts();
  } catch (error) {
    console.error('Error loading products:', error);
    showToast('Failed to load products. Using mock data instead.', 'error');
    // Fallback to mock data if API fails
    allProducts = mockProducts;
    filteredProducts = allProducts;
    renderProducts();
  }
}

function renderProducts() {
  if (!filteredProducts.length) {
    productsGrid.innerHTML = '<div class="loading">No products found</div>';
    return;
  }
  
  productsGrid.innerHTML = '';
  filteredProducts.forEach(product => {
    const productCard = createProductCard(product);
    productsGrid.appendChild(productCard);
  });
}

function createProductCard(product) {
  const card = document.createElement('div');
  card.className = 'product-card';
  
  const stars = '★'.repeat(Math.floor(product.rating)) + '☆'.repeat(5 - Math.floor(product.rating));
  
  card.innerHTML = `
    <img src="${product.image}" alt="${product.name}" class="product-image" loading="lazy">
    <div class="product-details">
      <div class="product-category">${product.category}</div>
      <h3 class="product-name">${product.name}</h3>
      <p class="product-price">${formatPrice(product.price)}</p>
      <div class="product-rating">
        <span class="stars">${stars}</span>
        <span>(${product.rating})</span>
      </div>
      <button class="add-to-cart" onclick="addToCart('${product.productId}')">
        Add to Cart
      </button>
    </div>
  `;
  
  return card;
}

// Search and Filter Functions
function handleSearch(e) {
  const searchTerm = e.target.value.toLowerCase();
  filteredProducts = allProducts.filter(product => 
    product.name.toLowerCase().includes(searchTerm) ||
    product.description.toLowerCase().includes(searchTerm) ||
    product.category.toLowerCase().includes(searchTerm)
  );
  applyFiltersAndSort();
}

function handleCategoryFilter(e) {
  // Remove active class from all buttons
  categoryBtns.forEach(btn => btn.classList.remove('active'));
  // Add active class to clicked button
  e.target.classList.add('active');
  
  currentCategory = e.target.dataset.category;
  applyFiltersAndSort();
}

function handleSort(e) {
  currentSort = e.target.value;
  applyFiltersAndSort();
}

function openModal(type) {
  const modal = document.getElementById(`${type}-modal`);
  modal.style.display = 'block';
}

function closeModal(type) {
  const modal = document.getElementById(`${type}-modal`);
  modal.style.display = 'none';
}

function switchModal(type) {
  closeModal(type === 'login' ? 'register' : 'login');
  openModal(type);
}

function toggleUserDropdown() {
  const dropdown = document.getElementById('user-dropdown');
  dropdown.classList.toggle('show');
}

async function handleLogin(e) {
  e.preventDefault();
  const username = document.getElementById('login-username').value;
  const password = document.getElementById('login-password').value;
  const errorMessage = document.getElementById('login-error-message');

  // Mock authentication
  if (username && password) {
    const user = {
      id: '123',
      username: username,
      email: username + '@example.com',
      role: username === 'admin' ? 'manager' : 'customer'
    };
    
    currentUser = user;
    updateAuthState();
    closeModal('login');
    showToast(`Welcome back, ${username}!`);
    
    // Clear form
    document.getElementById('loginForm').reset();
    errorMessage.style.display = 'none';
  } else {
    errorMessage.textContent = 'Please enter username and password';
    errorMessage.style.display = 'block';
  }
}

async function handleRegister(e) {
  e.preventDefault();
  const username = document.getElementById('register-username').value;
  const email = document.getElementById('register-email').value;
  const password = document.getElementById('register-password').value;
  const confirmPassword = document.getElementById('register-confirm-password').value;
  const errorMessage = document.getElementById('register-error-message');

  // Reset error message
  errorMessage.style.display = 'none';

  // Validate password match
  if (password !== confirmPassword) {
    errorMessage.textContent = 'Passwords do not match';
    errorMessage.style.display = 'block';
    return;
  }

  // Validate password length
  if (password.length < 6) {
    errorMessage.textContent = 'Password must be at least 6 characters long';
    errorMessage.style.display = 'block';
    return;
  }

  // Mock registration
  const user = {
    id: Date.now().toString(),
    username: username,
    email: email,
    role: 'customer'
  };
  
  currentUser = user;
  updateAuthState();
  closeModal('register');
  showToast(`Welcome to Marketly, ${username}!`);
  
  // Clear form
  document.getElementById('registerForm').reset();
}

function logout() {
  currentUser = null;
  likedProducts.clear();
  cart = { items: [] };
  updateAuthState();
  showToast('Logged out successfully');
}

function applyFiltersAndSort() {
  // Apply category filter
  let products = currentCategory === 'all' 
    ? allProducts 
    : allProducts.filter(product => product.category === currentCategory);
  
  // Apply search filter
  const searchTerm = searchBar.value.toLowerCase();
  if (searchTerm) {
    products = products.filter(product => 
      product.name.toLowerCase().includes(searchTerm) ||
      product.description.toLowerCase().includes(searchTerm) ||
      product.category.toLowerCase().includes(searchTerm)
    );
  }
  
  // Apply sorting
  switch (currentSort) {
    case 'price-low':
      products.sort((a, b) => a.price - b.price);
      break;
    case 'price-high':
      products.sort((a, b) => b.price - a.price);
      break;
    case 'name':
      products.sort((a, b) => a.name.localeCompare(b.name));
      break;
    case 'rating':
      products.sort((a, b) => b.rating - a.rating);
      break;
    default:
      // Keep original order
  }
  
  filteredProducts = products;
  renderProducts();
}

// Cart Functions
function addToCart(productId) {
  const product = allProducts.find(p => p.productId === productId);
  if (!product) return;

  const cartItem = cart.items.find(item => item.productId === productId);
  if (cartItem) {
    cartItem.quantity += 1;
  } else {
    cart.items.push({
      productId: productId,
      name: product.name,
      price: product.price,
      image: product.image,
      quantity: 1
    });
  }

  updateCart();
  showToast(`${product.name} added to cart!`);
}

function toggleLike(productId) {
  if (likedProducts.has(productId)) {
    likedProducts.delete(productId);
    showToast('Removed from favorites');
  } else {
    likedProducts.add(productId);
    showToast('Added to favorites');
  }
  renderProducts();
}

function removeFromCart(productId) {
  cart.items = cart.items.filter(item => item.productId !== productId);
  updateCartCount();
  updateCartDisplay();
  showToast('Item removed from cart', 'warning');
}

function updateQuantity(productId, change) {
  const item = cart.items.find(item => item.productId === productId);
  if (!item) return;
  
  item.quantity += change;
  
  if (item.quantity <= 0) {
    removeFromCart(productId);
    return;
  }
  
  updateCartCount();
  updateCartDisplay();
}

function updateCartCount() {
  const totalItems = cart.items.reduce((total, item) => total + item.quantity, 0);
  cartCount.textContent = totalItems;
}

function openCartModal() {
  updateCartDisplay();
  cartModal.style.display = 'block';
}

function closeCartModal() {
  cartModal.style.display = 'none';
}

function updateCartDisplay() {
  if (!cart.items.length) {
    cartItems.innerHTML = '<p style="text-align: center; padding: 2rem; color: #666;">Your cart is empty</p>';
    updateCartTotals(0, 0, 0, 0);
    return;
  }
  
  cartItems.innerHTML = '';
  cart.items.forEach(item => {
    const product = allProducts.find(p => p.productId === item.productId);
    if (!product) return;
    
    const cartItem = document.createElement('div');
    cartItem.className = 'cart-item';
    cartItem.innerHTML = `
      <img src="${product.image}" alt="${product.name}">
      <div class="cart-item-details">
        <div class="cart-item-name">${product.name}</div>
        <div class="cart-item-price">${formatPrice(product.price)}</div>
        <div class="quantity-controls">
          <button class="quantity-btn" onclick="updateQuantity('${item.productId}', -1)">-</button>
          <span class="quantity">${item.quantity}</span>
          <button class="quantity-btn" onclick="updateQuantity('${item.productId}', 1)">+</button>
          <button class="remove-item" onclick="removeFromCart('${item.productId}')">Remove</button>
        </div>
      </div>
    `;
    cartItems.appendChild(cartItem);
  });
  
  calculateCartTotals();
}

function calculateCartTotals() {
  const subtotal = cart.items.reduce((total, item) => {
    const product = allProducts.find(p => p.productId === item.productId);
    return total + (product.price * item.quantity);
  }, 0);
  
  const vat = subtotal * 0.1; // 10% VAT
  const deliveryFee = calculateDeliveryFee();
  const total = subtotal + vat + deliveryFee;
  
  updateCartTotals(subtotal, vat, deliveryFee, total);
}

function updateCartTotals(subtotal, vat, deliveryFee, total) {
  cartSubtotal.textContent = formatPrice(subtotal);
  cartVat.textContent = formatPrice(vat);
  deliveryFeeElement.textContent = formatPrice(deliveryFee);
  cartTotalAmount.textContent = formatPrice(total);
}

// Delivery Functions
function handleDeliveryOptionChange(e) {
  deliveryOptions.forEach(option => option.classList.remove('selected'));
  e.currentTarget.classList.add('selected');
  
  deliveryMethod = e.currentTarget.dataset.delivery;
  
  if (deliveryMethod === 'rush') {
    rushDeliveryInfo.classList.add('show');
  } else {
    rushDeliveryInfo.classList.remove('show');
  }
  
  calculateCartTotals();
}


function checkAuthState() {
  // In a real app, check localStorage/sessionStorage for saved session
  updateAuthState();
}

function updateAuthState() {
  if (currentUser) {
    authButtons.style.display = 'none';
    userMenu.style.display = 'block';
    document.getElementById('username-display').textContent = currentUser.username;
    
    // Show manager link for managers
    if (currentUser.role === 'manager') {
      document.getElementById('manager-link').style.display = 'block';
    }
  } else {
    authButtons.style.display = 'flex';
    userMenu.style.display = 'none';
    document.getElementById('manager-link').style.display = 'none';
  }
}

function renderProducts() {
  productsGrid.innerHTML = '';
  filteredProducts.forEach(product => {
    const productCard = document.createElement('div');
    productCard.className = 'product-card';
    productCard.innerHTML = `
      <img src="${product.image}" alt="${product.name}" class="product-image">
      <div class="product-details">
        <div class="product-category">${product.category}</div>
        <div class="product-name">${product.name}</div>
        <div class="product-price">$${product.price.toLocaleString()}</div>
        <div class="product-rating">
          <span class="stars">${'★'.repeat(Math.floor(product.rating))}${'☆'.repeat(5 - Math.floor(product.rating))}</span>
          <span>(${product.rating})</span>
        </div>
        <div class="product-actions">
          <button class="add-to-cart" data-id="${product.productId}">Add to Cart</button>
          <button class="like-btn ${likedProducts.has(product.productId) ? 'liked' : ''}" data-id="${product.productId}">
            ♥
          </button>
        </div>
      </div>
    `;
    productsGrid.appendChild(productCard);
  });

  // Add event listeners to buttons
  document.querySelectorAll('.add-to-cart').forEach(button => {
    button.addEventListener('click', () => addToCart(button.dataset.id));
  });

  document.querySelectorAll('.like-btn').forEach(button => {
    button.addEventListener('click', () => toggleLike(button.dataset.id));
  });
}


function calculateDeliveryFee() {
  const province = deliveryProvinceSelect.value;
  const subtotal = cart.items.reduce((total, item) => {
    const product = allProducts.find(p => p.productId === item.productId);
    return total + (product.price * item.quantity);
  }, 0);
  
  if (deliveryMethod === 'rush') {
    if (province === 'hanoi') {
      return 50000; // Rush delivery fee for Hanoi
    } else {
      return 0; // Rush delivery not available outside Hanoi
    }
  } else {
    // Standard delivery
    if (subtotal >= 100000) {
      return 0; // Free delivery for orders over 100,000 VND
    } else {
      switch (province) {
        case 'hanoi':
        case 'hcmc':
        case 'danang':
          return 30000;
        default:
          return 50000;
      }
    }
  }
}

// Order Form Functions
async function handleOrderSubmission(e) {
  e.preventDefault();
  
  const formData = new FormData(orderForm);
  const orderData = {
    userId: MOCK_USER_ID,
    items: cart.items,
    deliveryMethod: deliveryMethod,
    shippingAddress: {
      fullName: formData.get('fullName'),
      phone: formData.get('phone'),
      email: formData.get('email'),
      address: formData.get('address'),
      province: formData.get('province'),
      notes: formData.get('notes')
    },
    paymentMethod: formData.get('paymentMethod')
  };

  try {
    const response = await fetch(`${API_BASE_URL}/orders`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(orderData)
    });

    if (!response.ok) {
      throw new Error('Failed to create order');
    }

    const order = await response.json();
    
    // Clear cart after successful order
    cart = { userId: MOCK_USER_ID, items: [] };
    saveCartToStorage();
    updateCartCount();
    closeCartModal();
    
    showToast('Order placed successfully!');
    
    // If payment method is online, redirect to payment page
    if (formData.get('paymentMethod') === 'online') {
      window.location.href = `${API_BASE_URL}/payment/${order.orderId}`;
    }
  } catch (error) {
    console.error('Error submitting order:', error);
    showToast('Failed to place order', 'error');
  }
}

// Utility Functions
function formatPrice(price) {
  return new Intl.NumberFormat('vi-VN', {
    style: 'currency',
    currency: 'VND'
  }).format(price);
}

function showToast(message, type = 'success') {
  toast.textContent = message;
  toast.className = `toast ${type} show`;
  
  setTimeout(() => {
    toast.classList.remove('show');
  }, 3000);
}

// Additional helper functions for cart persistence (optional)
function saveCartToStorage() {
  try {
    localStorage.setItem('marketly_cart', JSON.stringify(cart));
  } catch (error) {
    console.warn('Could not save cart to localStorage:', error);
  }
}

function loadCartFromStorage() {
  try {
    const savedCart = localStorage.getItem('marketly_cart');
    if (savedCart) {
      cart = JSON.parse(savedCart);
      updateCartCount();
    }
  } catch (error) {
    console.warn('Could not load cart from localStorage:', error);
  }
}

// Call this to enable cart persistence
// loadCartFromStorage();