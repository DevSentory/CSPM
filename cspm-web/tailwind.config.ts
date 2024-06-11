import type { Config } from 'tailwindcss'

const config = {
  darkMode: ['class'],
  content: [
    './pages/**/*.{ts,tsx}',
    './components/**/*.{ts,tsx}',
    './app/**/*.{ts,tsx}',
    './src/**/*.{ts,tsx}',
  ],
  prefix: '',
  theme: {
    container: {
      center: true,
      padding: '2rem',
      screens: {
        '2xl': '1400px',
      },
    },
    extend: {
      gridTemplateColumns: {
        '21': 'repeat(21, minmax(0, 1fr))',
        '17': 'repeat(17, minmax(0, 1fr))',
        '13': 'repeat(13, minmax(0, 1fr))',
        footer: '200px minmax(900px, 1fr) 100px',
      },
      colors: {
        kyboNavy: '#002865',
        dimGray: '#22202288',
        red: '#dc506c',
        liteBule: '#ADD8E6',
        orange: '#FFA500',
        border: 'hsl(var(--border))',
        background: 'hsl(var(--background))',
        foreground: 'hsl(var(--foreground))',
        primary: {
          DEFAULT: 'hsl(var(--primary))',
          foreground: 'hsl(var(--primary-foreground))',
        },
        secondary: {
          DEFAULT: 'hsl(var(--secondary))',
          foreground: 'hsl(var(--secondary-foreground))',
        },
        destructive: {
          DEFAULT: 'hsl(var(--destructive))',
          foreground: 'hsl(var(--destructive-foreground))',
        },
        muted: {
          DEFAULT: 'hsl(var(--muted))',
          foreground: 'hsl(var(--muted-foreground))',
        },
        accent: {
          DEFAULT: 'hsl(var(--accent))',
          foreground: 'hsl(var(--accent-foreground))',
        },
        popover: {
          DEFAULT: 'hsl(var(--popover))',
          foreground: 'hsl(var(--popover-foreground))',
        },
        card: {
          DEFAULT: 'hsl(var(--card))',
          foreground: 'hsl(var(--card-foreground))',
        },
      },
      borderRadius: {
        lg: 'var(--radius)',
        md: 'calc(var(--radius) - 2px)',
        sm: 'calc(var(--radius) - 4px)',
      },
      keyframes: {
        'accordion-down': {
          from: { height: '0' },
          to: { height: 'var(--radix-accordion-content-height)' },
        },
        'accordion-up': {
          from: { height: 'var(--radix-accordion-content-height)' },
          to: { height: '0' },
        },
      },
      animation: {
        'accordion-down': 'accordion-down 0.2s ease-out',
        'accordion-up': 'accordion-up 0.2s ease-out',
      },
      fontSize: {
        xs: '0.75rem', // 예시: 매우 작은 텍스트 크기
        sm: '0.875rem', // 예시: 작은 텍스트 크기
        base: '1rem', // 예시: 기본 텍스트 크기
        lg: '1.125rem', // 예시: 큰 텍스트 크기
        xl: '1.25rem', // 예시: 매우 큰 텍스트 크기
        '2xl': '1.5rem', // 예시: 아주 큰 텍스트 크기
        // 원하는대로 추가할 수 있습니다.
      },
    },
  },
  plugins: [require('tailwindcss-animate')],
} satisfies Config

export default config
