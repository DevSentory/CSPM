'use client'

import { useMetaResuet } from '@/components/Zustand/store'

import ButtonLayer from '@/components/ui/buttonLayer'
import { useEffect } from 'react'

export default function FillterResetButtion() {
  const { setRequestMeta } = useMetaResuet()

  const handleReset = () => {
    setRequestMeta()
  }

  useEffect(() => {
    setRequestMeta()
  }, [setRequestMeta])
  return <ButtonLayer method={handleReset} childText='필터 해제' buttonStyle='bg-gray-500' />
}
