import { Button } from '@/components/ui/button'

interface Props {
  buttonStyle: string
  method?: () => void
  childText: string
}
export default function ButtonLayer({ buttonStyle, method, childText }: Props) {
  return (
    <Button
      className={`flex h-9 w-24 items-center justify-center rounded text-base text-white hover:bg-gray-400 ${buttonStyle}`}
      onClick={method}
      size='sm'
    >
      {childText}
    </Button>
  )
}
