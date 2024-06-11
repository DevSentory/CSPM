/** @type {import('next').NextConfig} */
const nextConfig = {
  output: 'standalone',
  reactStrictMode: true,
  webpack: (config) => {
    config.module.rules.push({
      test: /\.svg$/,
      use: ['@svgr/webpack'],
    })

    return config
  },
  env: {
    NEXT_PUBLIC_NEXT_APP_BASE_URL: process.env.NEXT_APP_BASE_URL,
    NEXT_PUBLIC_NEXT_APP_DOMAIN_URL: process.env.NEXT_APP_DOMAIN_URL,
  },
}

export default nextConfig
