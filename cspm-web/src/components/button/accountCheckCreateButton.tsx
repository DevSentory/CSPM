'use client'

import { useInputStore } from '@/components/Zustand/accountPageStore'
import ButtonLayer from '@/components/ui/buttonLayer'
import Link from 'next/link'

export default function TargetRegistrationButton() {
  const { setInputReset } = useInputStore()
  const handleAccountReset = () => {
    setInputReset()
  }
  return (
    <Link href={'/config/account/accountadd'}>
      <ButtonLayer buttonStyle='bg-kyboNavy ' method={handleAccountReset} childText='대상 등록' />
    </Link>
  )
}
