'use client'

import ButtonLayer from '@/components/ui/buttonLayer'
import Link from 'next/link'

export default function AccountCreateCancelButton() {
  return (
    <Link href={'/config/account'}>
      <ButtonLayer buttonStyle='bg-kyboNavy' childText='취소' />
    </Link>
  )
}
