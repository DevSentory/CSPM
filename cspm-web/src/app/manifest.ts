import { MetadataRoute } from 'next'

export default function mainifest(): MetadataRoute.Manifest {
  return {
    name: 'KYOBO DTS CSPM',
    short_name: 'KYOBO DTS CSPM',
    description: 'KYOBO DTS CSPM',
    start_url: '/',
    display: 'standalone',
    background_color: '#FFFFFF',
    theme_color: '#FFFFFF',

    icons: [
      {
        src: '/koybo.png',
        sizes: '512x512',
        type: 'image/png',
        purpose: 'maskable',
      },
      {
        src: '/koybo144.png',
        sizes: '144x144',
        type: 'image/png',
        purpose: 'any',
      },
    ],
  }
}
