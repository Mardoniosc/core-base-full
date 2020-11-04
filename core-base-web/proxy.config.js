const proxy = [
  {
    context: '/api',
    // target: 'https://api-ccos.abservicosdf.com',
    target: 'http://localhost:7780',
    secure: false,
    changeOrigin: true,
    logLevel: 'debug',
    pathRewrite: {'^/api' : ''}
  }
];
module.exports = proxy;
