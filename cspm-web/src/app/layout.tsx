import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import './globals.css'
import Menu from '@/components/menu'

const inter = Inter({ subsets: ['latin'] })

export const metadata: Metadata = {
  title: 'KYOBO DTS CSPM',
  description: 'DTS_CSPM, AWS 계정 자원들을 탐지하여 취약점 결과를 제공',
  manifest: 'manifest.ts',
  icons: {
    icon: '/koybo.png',
  },
}

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode
}>) {
  return (
    <html lang='en'>
      <head>
        <meta name='theme-color' content='#FFFFFF' />
      </head>
      <body className={inter.className}>
        <Menu />
        {children}
      </body>
    </html>
  )
}
